package dependencies.classesinfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClassDependencies {

    private String modelicaPath;
    private List<String> dependencies;


    public void setModelicaPath(String modelicaPath) {
        this.modelicaPath = modelicaPath;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }


    public void setParentClasses(List<String> parentClasses) {
        this.parentClasses = parentClasses;
    }

    public void setConstrainingClasses(Map<String, String> constrainingClasses) {
        this.constrainingClasses = constrainingClasses;
    }

    private List<String> parentClasses;
    private Map<String, String> constrainingClasses = Map.of();
    public boolean areAllDependenciesResolved = false;

    @Override
    public String toString() {
        return "{modelicaPath: \"" + modelicaPath + "\", " +
                "dependencies: [" + String.join(
                ", ", dependencies.stream()
                        .map(it -> "\"" + it + "\"")
                        .toList()) + "]" + ", parentClasses: ["
                + String.join(
                        ", ",
                parentClasses.stream()
                        .map(it -> "\"" + it + "\"")
                        .toList()
        )+ "], constrainingClasses: {" +
                String.join(
                        ", ",
                        constrainingClasses.keySet().stream().map( it ->
                        "{\"" + it + "\": \"" + constrainingClasses.get(it) + "\"}")
                                .toList()
                ) + "}}";
    }

    public boolean isAffected(String... modelicaClass) {
        return !Arrays.stream(modelicaClass).filter(it -> dependencies.contains(it)).toList().isEmpty();
    }

    public void setAreAllDependenciesResolved(boolean areResolved){
        this.areAllDependenciesResolved = areResolved;
    }
}
