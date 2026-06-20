package objects.modelica;

import modelica.ModelicaPathJoiner;
import modelica.PathMatcher;

import java.util.ArrayList;

public class ModelicaPackage implements IModelicaClass {

    private final String name;
    private final String path;
    private ModelicaClass parent = null;
    private final ArrayList<IModelicaClass> children = new ArrayList<>();
    private int currentPosition = -1;

    public ModelicaPackage(String name, ModelicaClass parent) {
        this.name = name;
        if (parent == null) {
            this.path = name;
        } else {
            this.parent = parent;
            this.path = ModelicaPathJoiner.joinPaths(parent.getPath(), name);
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


    @Override
    public IModelicaClass getParent() {
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
            currentPosition++;
        }
        return currentPosition <= children.size();
    }

    @Override
    public IModelicaClass getNext() {
        IModelicaClass nextModelicaClass;
        if (currentPosition < 0) {
            nextModelicaClass = this;
            currentPosition++;
            return nextModelicaClass;
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
