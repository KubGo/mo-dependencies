package dependencies.classesinfo;

import java.util.List;

public record DependenciesRecord(String modelicaPath, List<String> dependencies) {

    @Override
    public String toString() {
        return "{modelicaPath: \"" + modelicaPath + "\", dependencies: ["
                + String.join(", ", dependencies.stream().map(it -> "\"" + it + "\"").toList()) + "]}";
    }
}
