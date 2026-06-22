package objects.definitions;

import modelica.ModelicaClassType;
import objects.modelica.IModelicaClass;

import java.util.ArrayList;
import java.util.List;

public class PackageDefinition implements IClassDefinition {

	private final DeclarationBuilder declarationBuilder = new DeclarationBuilder();
	private final ArrayList<IClassDefinition> children = new ArrayList<>();
	ArrayList<Declaration> declarations = new ArrayList<>();
	String className;
	String classPath;
	private int currentPosition = 0;

	public PackageDefinition(IModelicaClass modelicaClass) {
		className = modelicaClass.getName();
		classPath = modelicaClass.getPath();
	}

	public void addChild(IClassDefinition child) {
		declarationBuilder.reset();
		declarationBuilder.setClassName(child.getClassName());
		declarations.add(declarationBuilder.createDeclaration());
		children.add(child);
	}

	@Override
	public ModelicaClassType getClassType() {
		return ModelicaClassType.PACKAGE;
	}

	@Override
	public IClassDefinition getNext() {
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
	public List<Declaration> getDeclarations() {
		return List.of();
	}

	@Override
	public List<Modification> getModifications() {
		return List.of();
	}
}
