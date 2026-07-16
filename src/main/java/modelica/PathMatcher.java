package modelica;

import java.util.List;

public class PathMatcher {
    public static boolean isSubPath(String classPath, String path) {
        List<String> pathParts = ModelicaPath.modelicaPathToSubPaths(path).reversed();
        List<String> classPathParts = ModelicaPath.modelicaPathToSubPaths(classPath).reversed();
        int i = 0;
        if (classPath.equals(path)) {
            return true;
        }
        for (String pathPart : pathParts) {
            if (!pathPart.equals(classPathParts.get(i))) {
                return false;
            }
            i++;
            if (i >= classPathParts.size()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isImportedPath(String classPath, String importedPath) {
        String[] classPathSplit = classPath.split("\\.");
        String[] importPathSplit = importedPath.split("\\.");

        return classPathSplit[0].equals(importPathSplit[importPathSplit.length - 1]);
    }

}
