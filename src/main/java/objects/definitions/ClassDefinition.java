package objects.definitions;

import lombok.Setter;
import modelica.ModelicaClassType;
import objects.modelica.IModelicaFile;

import java.util.ArrayList;
import java.util.List;

public class ClassDefinition implements IClassDefinition {
	ArrayList<Component> components = new ArrayList<>();
	ArrayList<Modification> modifications = new ArrayList<>();
	String className;
	String classPath;
	@Setter
    ModelicaClassType classType;
	private boolean resolved = false;

	public ClassDefinition(IModelicaFile modelicaClass) {
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

}
