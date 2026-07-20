package objects.modelica;

import lombok.Getter;
import modelica.ModelicaPath;
import modelica.PathMatcher;
import objects.files.IModelicaFile;

import java.util.List;

public class Modification implements
        IImportResolver,
        IDeclarationsResolver,
        IConstrainable,
        IRelativePathResolver {
    @Getter
    String component;
    @Getter
    String value;
    @Getter
    String constrainingClass = null;

    public Modification(String component, String value, String constrainingClass) {
        this.component = component;
        this.value = value;
        this.constrainingClass = constrainingClass;
    }

    public Modification(String component, String value) {
        this(component, value, null);
    }

    @Override
    public boolean hasConstraint() {
        return constrainingClass != null;
    }

    @Override
    public void resolveImport(String importedPath) {
        if (!hasConstraint()) {
            return;
        }
        if (PathMatcher.isImportedPath(this.constrainingClass, importedPath)) {
            constrainingClass = ModelicaPath.joinSubPaths(importedPath, this.constrainingClass);
        }
    }

    @Override
    public Modification resolveDeclaration(List<Declaration> declarations) {
        for (Declaration declaration : declarations) {
            if (value.equals(declaration.declarationName)) {
                String updatedConstrainingClass = constrainingClass;
                if (declaration.hasConstraint()) {
                    updatedConstrainingClass = declaration.constrainingClass;
                }
                return new Modification(this.component, declaration.declarationClass, updatedConstrainingClass);
            }
        }
        return this;
    }

    @Override
    public void resolveRelativePath(IModelicaFile modelicaFile) {
        // TODO("Resolve relative paths to absolute")
    }
}
