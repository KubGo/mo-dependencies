package objects.modelica;

import modelica.ModelicaPathJoiner;
import modelica.PathMatcher;

public class ModelicaClass implements IModelicaClass {

    private final String name;
    private final String path;
    private ModelicaClass parent = null;

    public ModelicaClass(String name, ModelicaClass parent) {
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
}
