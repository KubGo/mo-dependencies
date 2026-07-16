package objects.classes;

import modelica.ModelicaClassType;
import objects.files.IModelicaFile;
import objects.modelica.Component;
import objects.modelica.ComponentBuilder;
import objects.modelica.Declaration;
import objects.modelica.Modification;

import java.util.ArrayList;
import java.util.List;

import static parser.ParseText.parseText;

public class ModelicaPackage implements IModelicaClass {

    private final ComponentBuilder componentBuilder = new ComponentBuilder();
    private final ArrayList<IModelicaClass> children = new ArrayList<>();
    ArrayList<Component> components = new ArrayList<>();
    String className;
    String classPath;
    private int currentPosition = -1;
    private ModelicaPackage parentPackage = null;

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
        if (currentPosition < 0) {
            currentPosition++;
            return this;
        }
        return children.get(currentPosition).getNext();
    }

    @Override
    public boolean hasNext() {
        if (currentPosition < 0) {
            return true;
        }
        if (!children.get(currentPosition).hasNext()) {
            if (currentPosition + 1 >= children.size()) {
                return false;
            } else {
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
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public List<Declaration> getDeclarations() {
        return List.of();
    }

    @Override
    public List<Modification> getModifications() {
        return List.of();
    }

    public void getClassDefinitions(String text) {
        var listener = parseText(text);
    }

    @Override
    public ModelicaPackage getParentPackage() {
        return this.parentPackage;
    }

    @Override
    public void setParentPackage(ModelicaPackage parentPackage) {
        this.parentPackage = parentPackage;
        parentPackage.addChild(this);
    }
}
