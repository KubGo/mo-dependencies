package dependencies.classesinfo;

import modelica.ModelicaClassType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builder for {@link ClassDependencies}
 */
public class ClassDependenciesBuilder {
    private String modelicaPath = "";
    private List<String> dependencies = List.of();
    private List<String> parentClasses = List.of();
    private Map<String, String> constrainingClasses = new HashMap<>();
    private ModelicaClassType modelicaClassType = ModelicaClassType.PACKAGE;
    private Map<String, String> componentDeclarations = new HashMap<>();
    private List<String> modifiedClasses = new ArrayList<>();

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
        classDependencies.setUsedClasses(dependencies);
        classDependencies.setParentClasses(parentClasses);
        classDependencies.setConstrainingClasses(constrainingClasses);
        classDependencies.setModelicaClassType(modelicaClassType);
        classDependencies.setComponentDeclarations(componentDeclarations);
        classDependencies.setModifiedClasses(modifiedClasses);
        return classDependencies;
    }

    public ClassDependenciesBuilder setConstrainingClasses(Map<String, String> constrainingClasses) {
        this.constrainingClasses = constrainingClasses;
        return this;
    }

    public ClassDependenciesBuilder setModelicaClassType(ModelicaClassType modelicaClassType) {
        this.modelicaClassType = modelicaClassType;
        return this;
    }

    public ClassDependenciesBuilder setComponentDeclarations(Map<String, String> componentDeclarations) {
        this.componentDeclarations = componentDeclarations;
        return this;
    }

    public ClassDependenciesBuilder setModifiedClasses(List<String> modifiedClasses) {
        this.modifiedClasses = modifiedClasses;
        return this;
    }
}