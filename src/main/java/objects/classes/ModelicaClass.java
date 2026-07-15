package objects.classes;

import lombok.Setter;
import modelica.ModelicaClassType;
import objects.files.IModelicaFile;
import objects.modelica.Component;
import objects.modelica.Modification;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.DefinitionsListener;
import parser.Modelica;
import parser.ModelicaLexer;

import java.util.ArrayList;
import java.util.List;

public class ModelicaClass implements IModelicaClass {
	ArrayList<Component> components = new ArrayList<>();
	ArrayList<Modification> modifications = new ArrayList<>();
	private final String className;
	private final String classPath;
	@Setter
    ModelicaClassType classType;
	private boolean resolved = false;

	public ModelicaClass(IModelicaFile modelicaClass) {
		className = modelicaClass.getName();
		classPath = modelicaClass.getPath();
	}

	public void addDeclaration(Component component) {
		components.add(component);
	}

	public void addModification(Modification modification) {
		modifications.add(modification);
	}

	public List<Component> getComponents() {
		return components;
	}

	@Override
	public List<Modification> getModifications() {
		return modifications;
	}

	@Override
	public String getClassPath() {
		return classPath;
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public List<Component> getDeclarations() {
		return List.of();
	}

	@Override
	public ModelicaClassType getClassType() {
		return classType;
	}

	@Override
	public IModelicaClass getNext() {
		resolved = true;
		return this;
	}

	@Override
	public boolean hasNext() {
		return !resolved;
	}

	@Override
	public void reset() {
		resolved = false;
	}

	public void getClassDefinitions(String text) {
		ModelicaLexer modelicaLexer = new ModelicaLexer(CharStreams.fromString(text));
		CommonTokenStream tokens = new CommonTokenStream(modelicaLexer);
		Modelica parser = new Modelica(tokens);
		parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
		DefinitionsListener definitionsListener = new DefinitionsListener();
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(definitionsListener, parser.stored_definition());
	}
}
