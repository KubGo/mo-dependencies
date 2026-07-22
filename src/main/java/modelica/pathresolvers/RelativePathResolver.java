package modelica.pathresolvers;

import lombok.Setter;
import modelica.ModelicaPath;
import objects.files.ModelicaFolder;

import java.util.Set;

public class RelativePathResolver {

    @Setter
    private ModelicaFolder modelicaFolder;

    public RelativePathResolver() {
        modelicaFolder = null;
    }

    public RelativePathResolver(ModelicaFolder modelicaFolder) {
        this.modelicaFolder = modelicaFolder;
    }

    public String resolvePath(String path) {
        ModelicaFolder currentFolder = modelicaFolder;
        while (currentFolder != null) {
            Set<String> possiblePaths = currentFolder.getChildrenPaths();
            String pathToSearch = ModelicaPath.joinPaths(currentFolder.getPath(), path);
            if (possiblePaths.contains(pathToSearch)) {
                return pathToSearch;
            }
            currentFolder = currentFolder.getParent();
        }
        return path;
    }


}
