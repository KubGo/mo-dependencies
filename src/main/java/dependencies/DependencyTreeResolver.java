package dependencies;

import dependencies.classesinfo.ClassDependencies;
import dependencies.classesinfo.ClassDependenciesResolver;
import dependencies.classesinfo.IClassDependencies;
import dependencies.classesinfo.ParentDependenciesResolver;
import dependencies.structureinfo.ClassInfo;
import dependencies.writedependencies.AbstractDependenciesWriter;
import files.ModelicaFileReader;
import filtering.IFilter;
import modelica.pathresolvers.FileStructurePathResolver;

import java.io.IOException;
import java.util.*;

public class DependencyTreeResolver {
	ModelicaFilesStructure filesStructure = new ModelicaFilesStructure();
	ModelicaFileReader modelicaFileReader = new ModelicaFileReader();
	private final ParentDependenciesResolver<ClassDependenciesResolver> parentDependenciesResolver = new ParentDependenciesResolver<>();
	private Map<String, ClassDependenciesResolver> dependencyTree = new TreeMap<>();
	private FileStructurePathResolver fileStructurePathResolver;
	private String libraryName = "";
	private String libraryPath;
	private final List<IFilter> filters = new ArrayList<>();

	public DependencyTreeResolver(IFilter... filters) {
		Arrays.stream(filters).forEach(this::addFilter);
	}

	public DependencyTreeResolver() {
	}


	public Map<String, ClassDependenciesResolver> getDependencyTree() {
		return dependencyTree;
	}

	public void addFilter(IFilter filter) {
		filters.add(filter);
	}

	public void addLibraryDependencies(Map<String, ? extends IClassDependencies> dependencies) {
		parentDependenciesResolver.addTreeForParentSearching(dependencies);
	}

	public void generateLibraryDependencies(
			String path,
			String libraryName){
		this.libraryPath = path;
		this.libraryName = libraryName;
		filesStructure.resolveFileStructure(path, libraryName);
		fileStructurePathResolver = new FileStructurePathResolver(filesStructure.tree);

		filesStructure.tree.forEach(
				(packageName, packageInfo) -> {
					List<ClassInfo> classInfoList = packageInfo.getClassDefinitions();
					classInfoList.forEach(classInfo -> {
						fileStructurePathResolver.setCurrentPackage(packageInfo.getModelicaPath());
						String modelicaPath = classInfo.getModelicaPath();
						String className = classInfo.getClassName();
						try {
							ClassDependenciesResolver classDependenciesResolver = new ClassDependenciesResolver(
									className,
									modelicaFileReader.readFile(
											classInfo.path));
							classDependenciesResolver.resolveInternalDependencies();
							classDependenciesResolver.resolveLibraryDependencies(fileStructurePathResolver);
							classDependenciesResolver.filter(filters);
							dependencyTree.put(modelicaPath, classDependenciesResolver);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					});
				});
		parentDependenciesResolver.addTreeForParentSearching(dependencyTree);
		dependencyTree = parentDependenciesResolver.resolveParentDependencies(dependencyTree);
	}

	public void includeParentsDependentClasses(){
		dependencyTree = parentDependenciesResolver.resolveParentDependencies(dependencyTree);

	}

	public IClassDependencies getClassDependencies(String className) {
		if (dependencyTree.containsKey(className)) {
			return dependencyTree.get(className).toClassDependencies();
		} else {
			return null;
		}
	}

	public TreeMap<String, ClassDependencies> getSimplifiedClassDependencies() {
		TreeMap<String, ClassDependencies> dependencies = new TreeMap<>();
		for (String key : dependencyTree.keySet()) {
			dependencies.put(key, dependencyTree.get(key).toClassDependencies());
		}
		return dependencies;
	}

	public void saveDependencies(AbstractDependenciesWriter... writers) {
		for (AbstractDependenciesWriter writer : writers) {
			writer.setLibraryName(libraryName);
			writer.setPath(libraryPath);
			try {
				writer.writeDependencies(getSimplifiedClassDependencies());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
