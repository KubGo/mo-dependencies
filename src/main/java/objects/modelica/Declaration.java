package objects.modelica;

import lombok.Getter;
import modelica.ModelicaClassType;
import modelica.ModelicaPath;
import modelica.PathMatcher;

import java.util.List;

public class Declaration implements IImportResolver {
    @Getter
    ModelicaClassType type;
    @Getter
    String declarationName;
    @Getter
    String declarationClass;
    @Getter
    List<Modification> modifications;
    @Getter
    String description;
    @Getter
    String constrainingClass;

    public Declaration(ModelicaClassType type, String declarationName, String declarationClass, List<Modification> modifications, String description, String constrainingClass) {
        this.type = type;
        this.declarationName = declarationName;
        this.declarationClass = declarationClass;
        this.modifications = modifications;
        this.description = description;
        this.constrainingClass = constrainingClass;
    }

    @Override
    public void resolveImport(String importedPath) {
        if (PathMatcher.isImportedPath(this.declarationClass, importedPath)) {
            declarationClass = ModelicaPath.joinSubPaths(importedPath, this.declarationClass);
        }
        if (constrainingClass == null) {
            return;
        }
        if (PathMatcher.isImportedPath(this.constrainingClass, importedPath)) {
            constrainingClass = ModelicaPath.joinSubPaths(importedPath, this.constrainingClass);
        }
    }
}
