package objects.classes;

import lombok.Setter;
import modelica.ModelicaClassType;
import modelica.PathMatcher;
import objects.files.IModelicaFile;
import objects.modelica.Component;
import objects.modelica.Declaration;
import objects.modelica.Modification;
import parser.DefinitionsListener;

import java.util.ArrayList;
import java.util.List;

import static parser.ParseText.parseText;

public class ModelicaClass implements IModelicaClass {
    private final String className;
    private final String classPath;
    ArrayList<Component> components = new ArrayList<>();
    ArrayList<Modification> modifications = new ArrayList<>();
    ArrayList<Declaration> declarations = new ArrayList<>();
    @Setter
    ModelicaClassType classType;
    private boolean resolved = false;
    private ModelicaPackage parentPackage = null;

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
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public List<Declaration> getDeclarations() {
        return declarations;
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
        DefinitionsListener listener = parseText(text);
        components = listener.getComponents();
        modifications = listener.getModifications();
        declarations = listener.getDeclarations();
        listener.getPackageName();
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

    @Override
    public boolean pathMatches(String path) {
        return PathMatcher.isSubPath(this.classPath, path);
    }
}
