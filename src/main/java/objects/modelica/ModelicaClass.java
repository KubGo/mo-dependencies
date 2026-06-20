package objects.modelica;

import modelica.ModelicaPathJoiner;
import modelica.PathMatcher;

public class ModelicaClass implements IModelicaClass {

    private final String name;
    private final String path;
    private ModelicaPackage parent = null;
    private boolean read = false;

    public ModelicaClass(String name, ModelicaPackage parent) {
        this.name = name;
        if (parent == null) {
            this.path = name;
        } else {
            this.parent = parent;
            this.path = ModelicaPathJoiner.joinPaths(parent.getPath(), name);
        }
    }

    public ModelicaClass(String name) {
        this(name, null);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean pathMatches(String path) {
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
        return !read;
    }

    @Override
    public IModelicaClass getNext() {
        read = true;
        return this;
    }

    @Override
    public void reset() {
        read = false;
    }
}
