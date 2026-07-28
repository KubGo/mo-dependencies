package objects.modelica;

import lombok.Getter;
import modelica.ComponentPrefix;
import modelica.ModelicaPath;
import modelica.ModelicaVariability;
import modelica.PathMatcher;
import modelica.pathresolvers.RelativePathResolver;
import objects.files.ModelicaFolder;

import java.util.List;

@Getter
public class Component implements
        IImportResolver,
        IDeclarationsResolver,
        IConstrainable,
        IRelativePathResolver,
        IModifiable,
        IRedeclarable<Component> {
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
    public void resolveRelativePath(ModelicaFolder modelicaFile) {
        RelativePathResolver relativePathResolver = new RelativePathResolver(modelicaFile);
        className = relativePathResolver.resolvePath(className);
    }

    @Override
    public void modify(Modification modification) {
        if (modification.component.equals(componentName)) {
            value = modification.value;
            if (modification.hasConstraint()) {
                constrainingClass = modification.constrainingClass;
            }
        }
    }

    @Override
    public Component redeclare(List<Redeclaration> redeclarations) {
        var redeclaration = redeclarations.stream()
                .filter(it -> it.getComponent()
                        .equals(componentName))
                .findFirst().orElse(null);
        if (redeclaration != null) {
            return new ComponentBuilder()
                    .setComponentName(componentName)
                    .setClassName(redeclaration.getClassName())
                    .setComponentPrefix(componentPrefix)
                    .setVariability(variability)
                    .setValue(value)
                    .setDescription(description)
                    .setConstrainingClass((redeclaration.hasConstraint()) ? redeclaration.getConstrainingClass() : constrainingClass)
                    .build();
        }
        return this;
    }
}
