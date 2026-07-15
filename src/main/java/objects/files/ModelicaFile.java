package objects.files;

import modelica.ModelicaPath;
import modelica.PathMatcher;

import java.io.File;
import java.util.StringJoiner;

public class ModelicaFile implements IModelicaFile {

    private final String name;
    private final String path;
    private ModelicaFolder parent = null;
    private boolean read = false;
    private String filePath = "";

    public ModelicaFile(String name, ModelicaFolder parent) {
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
    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public void setFilePath(String rootPath) {
        StringJoiner joiner = new StringJoiner(File.separator);
        joiner.add(rootPath);
        for (String p : this.path.split("\\.")) {
            if (!rootPath.endsWith(p)) {
                joiner.add(p);
            }
        }
        this.filePath = joiner + ".mo";
    }
}
