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

}
