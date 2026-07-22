package objects.files;

import exceptions.ModelicaClassNotFoundException;
import modelica.ModelicaPath;
import modelica.PathMatcher;

import java.io.File;
import java.util.*;

public class ModelicaFolder implements IModelicaFile {

    private final String name;
    private final String path;
    private final ArrayList<IModelicaFile> children = new ArrayList<>();
    private final TreeMap<String, IModelicaFile> tree = new TreeMap<>();
    private String filePath = "";
    private ModelicaFolder parent = null;
    private int currentPosition = -1;

    public ModelicaFolder(String name, ModelicaFolder parent) {
        this.name = name;
        if (parent == null) {
            this.path = name;
        } else {
            this.parent = parent;
            this.path = ModelicaPath.joinPaths(parent.getPath(), name);
            parent.addChildren(this);
        }
    }

    public ModelicaFolder(String name) {
        this(name, null);
    }

    public void addToParentTree(IModelicaFile child) {
        tree.put(child.getPath(), child);
        if (hasParent()) {
            parent.addToParentTree(child);
        }
    }

    public Set<String> getChildrenPaths() {
        return tree.keySet();
    }

    public void addChildren(IModelicaFile child) {
        children.add(child);
        addToParentTree(child);
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
        IModelicaFile file = tree.get(name);
        if (file == null) {
            throw new ModelicaClassNotFoundException(name);
        }
        return file;
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
            } else {
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

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public List<ModelicaFolder> getAllFolders() {
        return children.stream().filter(it -> it instanceof ModelicaFolder)
                .map(it -> (ModelicaFolder) it).toList();
    }

    public List<ModelicaFile> getAllFiles() {

        return children.stream().filter(it -> it instanceof ModelicaFile)
                .map(it -> (ModelicaFile) it).toList();
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
