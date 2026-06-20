package objects.definitions;

import modelica.ModelicaClassType;
import objects.modelica.IModelicaClass;

import java.util.ArrayList;

public class ClassDefinition {
	ArrayList<Declaration> declarations = new ArrayList<>();
	ArrayList<Modification> modifications = new ArrayList<>();
	String className;
	String classPath;
	ModelicaClassType classType;

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

	public ArrayList<Declaration> getDeclarations() {
		return declarations;
	}

	public String getClassPath() {
		return classPath;
	}

	public String getClassName() {
		return className;
	}

	public ModelicaClassType getClassType() {
		return classType;
	}

	public void setClassType(ModelicaClassType classType) {
		this.classType = classType;
	}
}
