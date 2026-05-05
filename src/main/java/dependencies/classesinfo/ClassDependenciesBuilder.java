package dependencies.classesinfo;

import java.util.List;
import java.util.Map;

public class ClassDependenciesBuilder {
    private String modelicaPath = "";
    private List<String> dependencies = List.of();
    private List<String> parentClasses = List.of();
    private Map<String, String> constrainingClasses = Map.of();
    private boolean areAllDependenciesResolved = false;

    public ClassDependenciesBuilder setModelicaPath(String modelicaPath) {
        this.modelicaPath = modelicaPath;
        return this;
    }

    public ClassDependenciesBuilder setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
        return this;
    }

    public ClassDependenciesBuilder setParentClasses(List<String> parentClasses) {
        this.parentClasses = parentClasses;
        return this;
    }

    public ClassDependencies build() {
        ClassDependencies classDependencies = new ClassDependencies();
        classDependencies.setModelicaPath(modelicaPath);
        classDependencies.setDependencies(dependencies);
        classDependencies.setParentClasses(parentClasses);
        classDependencies.setConstrainingClasses(constrainingClasses);
        return classDependencies;
    }

    public ClassDependenciesBuilder setConstrainingClasses(Map<String, String> constrainingClasses) {
        this.constrainingClasses = constrainingClasses;
        return this;
    }
}