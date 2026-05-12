package dependencies.classesinfo;

import dependencies.structureinfo.PackageInfo;
import filtering.IFilter;
import filtering.IFilterable;
import modelica.ModelicaClassType;
import modelica.pathresolvers.FileStructurePathResolver;
import modelica.pathresolvers.StandardImportPathResolver;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.ClassesListener;
import parser.ModelicaLexer;
import parser.ModelicaParser;

import java.util.*;
import java.util.stream.Collectors;

public class ClassDependenciesResolver implements IClassDependencies, IFilterable {
	private final List<String> importedClasses;
	private ArrayList<String> usedClasses = new ArrayList<>();
	private final List<String> parentClasses;
	private final boolean classDefinitionsResolved;
	private final String packageName;
	String className;
	private final Map<String, String> constrainingClassesMap;
	private final Map<String, String> classDefinitionsMap;
	private boolean standardImportsResolved;
	private final Set<String> resolvedLibraries = new HashSet<>();
	private final ModelicaClassType modelicaClassType;

	public ClassDependenciesResolver(String className, String text) {
		ModelicaLexer modelicaLexer = new ModelicaLexer(CharStreams.fromString(text));
		CommonTokenStream tokens = new CommonTokenStream(modelicaLexer);
		ModelicaParser parser = new ModelicaParser(tokens);
		ParseTree tree = parser.stored_definition();
		ParseTreeWalker walker = new ParseTreeWalker();
		ClassesListener listener = new ClassesListener();
		walker.walk(listener, tree);
		this.className = className;
		importedClasses = listener.imports;
		standardImportsResolved = importedClasses.isEmpty();
		usedClasses.addAll(listener.classes);
		parentClasses = listener.parentClasses;
		constrainingClassesMap = listener.constrainingClassesMap;
		classDefinitionsMap = listener.classDefinitionsMap;
		classDefinitionsResolved = classDefinitionsMap.isEmpty();
		packageName = listener.packageName;
		modelicaClassType = listener.modelicaClassType;
	}

	@Override
	public void filter(List<IFilter> filters) {
		for (IFilter filter : filters) {
			usedClasses = usedClasses.stream()
					.filter(filter::filterName)
					.collect(Collectors.toCollection(ArrayList::new));
		}
	}

	@Override
	public List<String> getParentClasses() {
		return parentClasses;
	}

	@Override
	public List<String> getClasses() {
		return usedClasses.stream().toList();
	}

	@Override
	public boolean areParentDependenciesResolved(List<String> librariesNames) {
		return resolvedLibraries.containsAll(librariesNames);
	}

	@Override
	public void setLibrariesResolved(List<String> librariesNames) {
		resolvedLibraries.addAll(librariesNames);
	}

	@Override
	public void addClassesUsedByParents(List<String> parentClasses) {
		Set<String> usedClassesSet = new HashSet<>(usedClasses);
		usedClassesSet.addAll(parentClasses);
		usedClasses = new ArrayList<>(usedClassesSet.stream().toList());
	}


	public void resolveInternalDependencies() {
		resolveClassDefinitions();
		resolverStandardImports();
	}

	public void resolveLibraryDependencies(FileStructurePathResolver pathResolver) {
		PackageInfo currentPackage = pathResolver.getPackageInfoByKey(packageName);
		if (currentPackage == null) {
			return;
		}
		pathResolver.setCurrentPackage(currentPackage);
		usedClasses.replaceAll(pathResolver::getAbsolutePath);
		parentClasses.replaceAll(pathResolver::getAbsolutePath);
	}

	private void resolverStandardImports() {
		if (!standardImportsResolved) {
			StandardImportPathResolver pathResolver = new StandardImportPathResolver();
			for (int i = 0; i < usedClasses.size(); i++) {
				for (String importedClass : importedClasses) {
					pathResolver.setImportPath(importedClass);
					if (pathResolver.isSubPath(usedClasses.get(i))) {
						usedClasses.set(i, pathResolver.getAbsolutePath(usedClasses.get(i)));
					}
				}
			}
			standardImportsResolved = true;
		}
	}

	private void resolveClassDefinitions() {
		if (!classDefinitionsResolved) {
			classDefinitionsMap.forEach((name, classPath) -> {
				int index = usedClasses.indexOf(name);
				if (index > 0) {
					usedClasses.set(index, classPath);
				}
			});
		}
	}

	public ClassDependencies toClassDependencies() {
		return new ClassDependenciesBuilder()
				.setModelicaPath(packageName + "." + className)
				.setDependencies(getClasses())
				.setParentClasses(this.parentClasses)
				.setConstrainingClasses(this.constrainingClassesMap).setModelicaClassType(this.modelicaClassType)
				.build();
	}
}
