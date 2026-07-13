package objects.modelica;

import modelica.ModelicaPath;
import modelica.PathMatcher;

import java.io.File;
import java.util.StringJoiner;

public class ModelicaFile implements IModelicaFile {

    private final String name;
    private final String path;
    private ModelicaPackage parent = null;
    private boolean read = false;

    public ModelicaFile(String name, ModelicaPackage parent) {
        this.name = name;
        if (parent == null) {
            this.path = name;
        } else {
            this.parent = parent;
            parent.addChildren(this);
            this.path = ModelicaPath.joinPaths(parent.getPath(), name);
        }
    }

    public ModelicaFile(String name) {
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
    public IModelicaFile getParent() {
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
    public IModelicaFile getNext() {
        read = true;
        return this;
    }

    @Override
    public void reset() {
        read = false;
    }

    @Override
    public String getFilePath(String rootPath) {
        StringJoiner joiner = new StringJoiner(File.separator);
        joiner.add(rootPath);
        for (String p : this.path.split("\\.")) {
            if (!rootPath.endsWith(p)) {
                joiner.add(p);
            }
        }
        return joiner + ".mo";
    }
}
