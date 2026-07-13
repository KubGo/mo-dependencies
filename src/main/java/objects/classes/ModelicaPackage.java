package objects.classes;

import modelica.ModelicaClassType;
import objects.files.IModelicaFile;
import objects.modelica.Component;
import objects.modelica.ComponentBuilder;
import objects.modelica.Modification;

import java.util.ArrayList;
import java.util.List;

public class ModelicaPackage implements IModelicaClass {

	private final ComponentBuilder componentBuilder = new ComponentBuilder();
	private final ArrayList<IModelicaClass> children = new ArrayList<>();
	ArrayList<Component> components = new ArrayList<>();
	String className;
	String classPath;
	private int currentPosition = 0;

	public ModelicaPackage(IModelicaFile modelicaClass) {
		className = modelicaClass.getName();
		classPath = modelicaClass.getPath();
	}

	public void addChild(IModelicaClass child) {
		componentBuilder.reset();
		componentBuilder.setClassName(child.getClassName());
		components.add(componentBuilder.build());
		children.add(child);
	}

	@Override
	public ModelicaClassType getClassType() {
		return ModelicaClassType.PACKAGE;
	}

	@Override
	public IModelicaClass getNext() {
		return children.get(currentPosition).getNext();
	}

	@Override
	public boolean hasNext() {
		if (!children.get(currentPosition).hasNext()) {
			if (currentPosition + 1 >= children.size()) {
				return false;
			}
			else {
				currentPosition++;
				return hasNext();
			}
		}
		return true;
	}

	@Override
	public void reset() {
		for (var child : children) {
			child.reset();
		}
		currentPosition = 0;
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
	public List<Modification> getModifications() {
		return List.of();
	}
}
