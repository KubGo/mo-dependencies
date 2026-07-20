package modelica;

import java.util.List;

import static java.lang.Math.min;

public class PathMatcher {
    /**
     * @param classPath - full path to the class
     * @param path      - subpath for testing
     * @return true if it matches the subpath
     */
    public static boolean isSubPath(String classPath, String path) {
        List<String> pathParts = ModelicaPath.modelicaPathToSubPaths(path).reversed();
        List<String> classPathParts = ModelicaPath.modelicaPathToSubPaths(classPath).reversed();
        return pathParts.equals(classPathParts.subList(0, min(pathParts.size(), classPathParts.size())));
    }

    public static boolean isImportedPath(String classPath, String importedPath) {
        String[] classPathSplit = classPath.split("\\.");
        String[] importPathSplit = importedPath.split("\\.");

        return classPathSplit[0].equals(importPathSplit[importPathSplit.length - 1]);
    }

}
