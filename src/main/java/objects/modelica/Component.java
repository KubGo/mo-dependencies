package objects.modelica;

import lombok.Getter;
import modelica.ComponentPrefix;
import modelica.ModelicaPath;
import modelica.ModelicaVariability;
import modelica.PathMatcher;

@Getter
public class Component implements IImportResolver {
    String componentName;
    String className;
    String description;
    String constrainingClass;
    String value;
    ModelicaVariability variability;
    ComponentPrefix componentPrefix;


    public Component(
            String componentName, String className, String description, String constrainingClass, String value,
            ModelicaVariability variability, ComponentPrefix componentPrefix) {
        this.componentName = componentName;
        this.className = className;
        this.description = description;
        this.constrainingClass = constrainingClass;
        this.value = value;
        this.variability = variability;
        this.componentPrefix = componentPrefix;
    }

    public boolean hasConstraint() {
        return constrainingClass != null;
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean hasClassDefinition() {
        return className != null;
    }

    @Override
    public void resolveImport(String importedPath) {
        if (PathMatcher.isImportedPath(this.className, importedPath)) {
            className = ModelicaPath.joinSubPaths(importedPath, this.className);
        }
    }
}
