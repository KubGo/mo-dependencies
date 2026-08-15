package objects.modelica;

import lombok.Getter;
import modelica.ModelicaClassType;
import modelica.ModelicaPath;
import modelica.PathMatcher;
import modelica.pathresolvers.RelativePathResolver;
import objects.files.ModelicaFolder;

import java.util.List;

public class Declaration implements
        IImportResolver,
        IConstrainable,
        IRelativePathResolver,
        IClassName {
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

    @Override
    public boolean hasConstraint() {
        return constrainingClass != null;
    }


    @Override
    public void resolveRelativePath(ModelicaFolder modelicaFile) {
        RelativePathResolver relativePathResolver = new RelativePathResolver(modelicaFile);
        declarationClass = relativePathResolver.resolvePath(declarationClass);
    }

    @Override
    public String getClassName() {
        return declarationClass;
    }
}
