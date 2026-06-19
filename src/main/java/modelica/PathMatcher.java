package modelica;

import java.util.Arrays;
import java.util.List;

public class PathMatcher {
    public static boolean isSubPath(String classPath, String path) {
        List<String> pathParts = getPathParts(path);
        List<String> classPathParts = getPathParts(classPath);
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

    private static List<String> getPathParts(String path) {
        List<String> pathParts;
        if (path.split("\\.").length < 2) {
            pathParts = List.of(path);
        } else {
            pathParts = Arrays.stream(path.split("\\.")).toList();
        }
        return pathParts.reversed();
    }
}
