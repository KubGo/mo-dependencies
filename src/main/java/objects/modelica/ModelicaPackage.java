package objects.modelica;

import exceptions.ModelicaClassNotFoundException;
import modelica.ModelicaPath;
import modelica.PathMatcher;

import java.util.ArrayList;

public class ModelicaPackage implements IModelicaClass {

    private final String name;
    private final String path;
    private ModelicaPackage parent = null;
    private final ArrayList<IModelicaClass> children = new ArrayList<>();
    private int currentPosition = -1;

    public ModelicaPackage(String name, ModelicaPackage parent) {
        this.name = name;
        if (parent == null) {
            this.path = name;
        } else {
            this.parent = parent;
            parent.addChildren(this);
            this.path = ModelicaPath.joinPaths(parent.getPath(), name);
        }
    }

    public ModelicaPackage(String name) {
        this(name, null);
    }

    public void addChildren(IModelicaClass child) {
        children.add(child);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public boolean pathMatches(String path) {
        for (IModelicaClass child : children) {
            if (child.pathMatches(path)) {
                return true;
            }
        }
        return PathMatcher.isSubPath(this.path, path);
    }

    public IModelicaClass getByName(String name) {
        if (!pathMatches(name)) {
            throw new ModelicaClassNotFoundException(name);
        }
        IModelicaClass searchedClass;
        for (var child : children) {
            if (child.pathMatches(name)) {
                return getModelicaClassByName(child, name);
            }
        }
        throw new ModelicaClassNotFoundException(name);
    }

    private IModelicaClass getModelicaClassByName(IModelicaClass modelicaClass, String name) {
        IModelicaClass searchedClass;
        while (modelicaClass.hasNext()) {
            searchedClass = modelicaClass.getNext();
            if (searchedClass.getPath().equals(name)) {
                modelicaClass.reset();
                return searchedClass;
            }
        }
        throw new ModelicaClassNotFoundException(name);
    }


    @Override
    public ModelicaPackage getParent() {
        return parent;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public boolean hasNext() {
        if (currentPosition < 0) {
            return true;
        }
        if (!children.get(currentPosition).hasNext()) {
            if (currentPosition + 1 >= children.size()) {
                return false;
            }
            else {
                return children.get(currentPosition + 1).hasNext();
            }
        }
        return true;
    }

    @Override
    public IModelicaClass getNext() {
        IModelicaClass nextModelicaClass;
        if (currentPosition < 0) {
            nextModelicaClass = this;
            currentPosition++;
            return nextModelicaClass;
        }
        if (!children.get(currentPosition).hasNext()) {
            currentPosition++;
        }
        return children.get(currentPosition).getNext();
    }

    @Override
    public void reset() {
        for (var child : children) {
            child.reset();
        }
        currentPosition = -1;
    }
}
