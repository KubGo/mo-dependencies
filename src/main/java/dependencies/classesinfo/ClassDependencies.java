package dependencies.classesinfo;

import modelica.PathJoiner;
import modelica.pathresolvers.StandardImportPathResolver;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.ClassesListener;
import parser.ModelicaLexer;
import parser.ModelicaParser;

import java.util.*;

public class ClassDependencies {
	String className;
	private final List<String> importedClasses;
	private final ArrayList<String> usedClasses = new ArrayList<>();
	private final List<String> parentClasses;
	private Map<String, String> constrainingClassesMap = new HashMap<>();
	private Map<String, String> classDefinitionsMap = new HashMap<>();
	private boolean standardImportsResolved;
	private boolean classDefinitionsResolved;

	public ClassDependencies(String className, String text) {
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
	}

	public List<String> getAbsolutePathsClassList(){
		resolverStandardImports();
		return usedClasses.stream().toList();
	}

	public void resolveInternalDependencies(){
		resolveClassDefinitions();
		resolverStandardImports();
	}

	private void resolverStandardImports(){
		if (!standardImportsResolved){
			StandardImportPathResolver pathResolver = new StandardImportPathResolver();
			for (int i = 0; i < usedClasses.size(); i++) {
				for (String importedClass: importedClasses){
					if (pathResolver.isSubPath(importedClass, usedClasses.get(i))){
						usedClasses.set(i, PathJoiner.joinPaths(importedClass, usedClasses.get(i)));
					}
				}
			}
			standardImportsResolved = true;
		}
	}

	private void resolveClassDefinitions(){
		if (!classDefinitionsResolved){
			classDefinitionsMap.forEach( (name, classPath) -> {
				int index = usedClasses.indexOf(name);
				if (index > 0){
					usedClasses.set(index, classPath);
				}
			} );
		}
	}
}
