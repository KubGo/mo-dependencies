package objects.files;

import exceptions.ModelicaClassNotFoundException;
import modelica.ModelicaPath;
import modelica.PathMatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.StringJoiner;

public class ModelicaFolder implements IModelicaFile {

    private final String name;
    private final String path;
    private String filePath = "";
    private ModelicaFolder parent = null;
    private final ArrayList<IModelicaFile> children = new ArrayList<>();
    private int currentPosition = -1;

    public ModelicaFolder(String name, ModelicaFolder parent) {
        this.name = name;
        if (parent == null) {
            this.path = name;
        } else {
            this.parent = parent;
            parent.addChildren(this);
            this.path = ModelicaPath.joinPaths(parent.getPath(), name);
        }
    }

    public ModelicaFolder(String name) {
        this(name, null);
    }

    public void addChildren(IModelicaFile child) {
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
        for (IModelicaFile child : children) {
            if (child.pathMatches(path)) {
                return true;
            }
        }
        return PathMatcher.isSubPath(this.path, path);
    }

    public IModelicaFile getByName(String name) {
        if (!pathMatches(name)) {
            throw new ModelicaClassNotFoundException(name);
        }
        for (var child : children) {
            if (child.pathMatches(name)) {
                return getModelicaClassByName(child, name);
            }
        }
        throw new ModelicaClassNotFoundException(name);
    }

    private IModelicaFile getModelicaClassByName(IModelicaFile modelicaClass, String name) {
        IModelicaFile searchedClass;
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
    public ModelicaFolder getParent() {
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
        if (currentPosition >= children.size()) {
            return false;
        }
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
    public IModelicaFile getNext() {
        IModelicaFile nextModelicaClass;
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
        joiner.add("package.mo");
        this.filePath = joiner.toString();
    }
}
