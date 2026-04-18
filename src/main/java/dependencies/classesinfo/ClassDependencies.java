package dependencies.classesinfo;

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
	private List<String> importedClasses;
	private Set<String> usedClasses;
	private List<String> parentClasses;
	private Map<String, String> constrainingClassesMap = new HashMap<>();
	private Map<String, String> classDefinitionsMap = new HashMap<>();

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
		usedClasses = listener.classes;
		parentClasses = listener.parentClasses;
		constrainingClassesMap = listener.constrainingClassesMap;
		classDefinitionsMap = listener.classDefinitionsMap;
	}

	public List<String> getAbsolutePathsClassList(){
		// TODO(Resolve classes to absolute paths)
		return usedClasses.stream().toList();
	}
}
