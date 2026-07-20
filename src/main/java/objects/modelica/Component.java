package objects.modelica;

import lombok.Getter;
import modelica.ComponentPrefix;
import modelica.ModelicaPath;
import modelica.ModelicaVariability;
import modelica.PathMatcher;
import objects.files.IModelicaFile;

import java.util.List;

@Getter
public class Component implements
        IImportResolver,
        IDeclarationsResolver,
        IConstrainable,
        IRelativePathResolver {
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

    @Override
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

    @Override
    public Component resolveDeclaration(List<Declaration> declarations) {
        for (Declaration declaration : declarations) {
            if (className.equals(declaration.declarationName)) {
                String updatedConstrainingClass = constrainingClass;
                if (declaration.hasConstraint()) {
                    updatedConstrainingClass = declaration.constrainingClass;
                }
                return new Component(
                        componentName,
                        declaration.declarationClass,
                        description,
                        updatedConstrainingClass,
                        value,
                        variability,
                        componentPrefix);
            }
        }
        return this;
    }

    @Override
    public void resolveRelativePath(IModelicaFile modelicaFile) {
        // TODO("Resolve relative path declarations to absolute path")
    }
}
