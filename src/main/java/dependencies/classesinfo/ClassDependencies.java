package dependencies.classesinfo;

import com.google.gson.annotations.SerializedName;
import modelica.ModelicaClassType;

import java.util.*;

/**
 * Simplified ClassDependencies that contains the class information, but doesn't resolve it from
 * Modelica file and can be serialized to save to file.
 */
public class ClassDependencies implements IClassDependencies {

    @SerializedName("ModelicaPath")
    private String modelicaPath;
    @SerializedName("resolvedLibraries")
    private Set<String> resolvedLibraries = new HashSet<>();
    @SerializedName("usedClasses")
    private List<String> usedClasses;
    @SerializedName("parentClasses")
    private List<String> parentClasses;

    @Override
    public Map<String, String> getComponentDeclarations() {
        return Map.of();
    }

    @Override
    public List<String> getModifiedClasses() {
        return List.of();
    }

    @SerializedName("constrainingClasses")
    private Map<String, String> constrainingClasses = new HashMap<>();
    @SerializedName("classType")
    private ModelicaClassType modelicaClassType;

    @Override
    public String getModelicaPath() {
        return modelicaPath;
    }

    @Override
    public List<String> getClasses() {
        return usedClasses;
    }

    @Override
    public List<String> getParentClasses() {
        return parentClasses;
    }

    @Override
    public void addParentClasses(List<String> parentClasses) {
        this.parentClasses.addAll(parentClasses);
    }

    @Override
    public void addClasses(List<String> classes) {
        Set<String> updatedClasses = new HashSet<>(usedClasses);
        updatedClasses.addAll(classes);
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

    @Override
    public IClassDependencies toSavableClassDependencies() {
        return this;
    }

    public void setModelicaPath(String modelicaPath) {
        this.modelicaPath = modelicaPath;
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

    public boolean isAffected(String... modelicaClass) {
        return !Arrays.stream(modelicaClass).filter(it -> usedClasses.contains(it)).toList().isEmpty();
    }

    @Override
    public String toString() {
        return "ClassDependencies{" + "modelicaPath='" + modelicaPath + '\'' + ", resolvedLibraries=" + resolvedLibraries + ", usedClasses=" + usedClasses + ", parentClasses=" + parentClasses + ", constrainingClasses=" + constrainingClasses + '}';
    }

    public ModelicaClassType getModelicaClassType() {
        return modelicaClassType;
    }

    @Override
    public boolean isModel() {
        return modelicaClassType == ModelicaClassType.MODEL;
    }

    public void setModelicaClassType(ModelicaClassType modelicaClassType) {
        this.modelicaClassType = modelicaClassType;
    }
}
