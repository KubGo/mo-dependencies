package modelica.pathresolvers;

import lombok.Setter;
import modelica.PathMatcher;
import objects.files.IModelicaFile;
import objects.files.ModelicaFolder;

import java.util.List;

import static modelica.ModelicaPath.modelicaPathToSubPaths;

public class RelativePathResolver {

    @Setter
    private IModelicaFile modelicaFile;

    public RelativePathResolver() {
        modelicaFile = null;
    }

    public RelativePathResolver(ModelicaFolder modelicaFile) {
        this.modelicaFile = modelicaFile;
    }

    public String resolvePath(String path) {
        modelicaFile.resetAll();
        List<String> pathParts = modelicaPathToSubPaths(path);
        int i = 0;
        IModelicaFile currentCorrectFile = modelicaFile.getParent();
        while (currentCorrectFile != null) {
            if (PathMatcher.isSubPath(currentCorrectFile.getPath(), path)) {
                return currentCorrectFile.getPath();
            }
            if (!currentCorrectFile.hasNext() || i > pathParts.size() - 1) {
                currentCorrectFile = currentCorrectFile.getParent();
                i = 0;
            } else {
                IModelicaFile modelicaFile = currentCorrectFile.getNext();
                if (PathMatcher.isSubPath(modelicaFile.getPath(), pathParts.get(i))) {
                    currentCorrectFile = modelicaFile;
                    i++;
                }
            }
        }
        return path;
    }


}
