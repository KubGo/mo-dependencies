package dependencies.classesinfo;

import java.util.List;

public class ClassDependencies {

    public String modelicaPath;
    public List<String> dependencies;

    public ClassDependencies(String modelicaPath, List<String> dependencies) {
        this.modelicaPath = modelicaPath;
        this.dependencies = dependencies;
    }

    @Override
    public String toString() {
        return "{modelicaPath: \"" + modelicaPath + "\", dependencies: [" + String.join(
                ", ", dependencies.stream()
                        .map(it -> "\"" + it + "\"")
                        .toList()) + "]}";
    }
}
