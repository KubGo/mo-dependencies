package dependencies.classesinfo;

import java.util.*;

public class ClassDependencies implements IClassDependencies {

    private String modelicaPath;
    private final Set<String> resolvedLibraries = new HashSet<>();
    public boolean areAllDependenciesResolved = false;
    private List<String> usedClasses;
    private List<String> parentClasses;
    private Map<String, String> constrainingClasses = Map.of();

    @Override
    public List<String> getClasses() {
        return usedClasses;
    }

    @Override
    public List<String> getParentClasses() {
        return parentClasses;
    }

    @Override
    public void addClassesUsedByParents(List<String> parentClasses) {
        Set<String> updatedClasses = new HashSet<>(usedClasses);
        updatedClasses.addAll(parentClasses);
        setUsedClasses(updatedClasses.stream().toList());
    }

    @Override
    public boolean areParentDependenciesResolved(List<String> librariesNames) {
        return resolvedLibraries.containsAll(librariesNames);
    }

    @Override
    public void setLibrariesResolved(List<String> librariesNames) {
        resolvedLibraries.addAll(librariesNames);
    }

    public void setModelicaPath(String modelicaPath) {
        this.modelicaPath = modelicaPath;
    }

    public List<String> getUsedClasses() {
        return usedClasses;
    }

    public void setUsedClasses(List<String> usedClasses) {
        this.usedClasses = usedClasses;
    }


    public void setParentClasses(List<String> parentClasses) {
        this.parentClasses = parentClasses;
    }

    public void setConstrainingClasses(Map<String, String> constrainingClasses) {
        this.constrainingClasses = constrainingClasses;
    }

    @Override
    public String toString() {
        return "{modelicaPath: \"" + modelicaPath + "\", " +
                "dependencies: [" + String.join(
                ", ", usedClasses.stream()
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
        return !Arrays.stream(modelicaClass).filter(it -> usedClasses.contains(it)).toList().isEmpty();
    }

    public void setAreAllDependenciesResolved(boolean areResolved){
        this.areAllDependenciesResolved = areResolved;
    }
}
