package objects.definitions;

import modelica.ModelicaClassType;
import objects.modelica.IModelicaClass;

import java.util.ArrayList;
import java.util.List;

public class ClassDefinition implements IClassDefinition {
	ArrayList<Declaration> declarations = new ArrayList<>();
	ArrayList<Modification> modifications = new ArrayList<>();
	String className;
	String classPath;
	ModelicaClassType classType;
	private boolean resolved = false;

	public ClassDefinition(IModelicaClass modelicaClass) {
		className = modelicaClass.getName();
		classPath = modelicaClass.getPath();
	}

	public void addDeclaration(Declaration declaration) {
		declarations.add(declaration);
	}

	public void addModification(Modification modification) {
		modifications.add(modification);
	}

	@Override
	public List<Declaration> getDeclarations() {
		return declarations;
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
	public ModelicaClassType getClassType() {
		return classType;
	}

	@Override
	public IClassDefinition getNext() {
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

	public void setClassType(ModelicaClassType classType) {
		this.classType = classType;
	}
}
