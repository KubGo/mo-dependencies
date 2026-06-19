package objects.modelica;

import modelica.ModelicaPathJoiner;
import modelica.PathMatcher;

import java.util.ArrayList;

public class ModelicaPackage implements IModelicaClass {

    private final String name;
    private final String path;
    private ModelicaClass parent = null;
    private final ArrayList<IModelicaClass> children = new ArrayList<>();

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
        if (!PathMatcher.isSubPath(this.path, path)) {
            return false;
        }
        for (IModelicaClass child : children) {
            if (child.pathMatches(path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IModelicaClass getParent() {
        return null;
    }

    @Override
    public boolean hasParent() {
        return false;
    }
}
