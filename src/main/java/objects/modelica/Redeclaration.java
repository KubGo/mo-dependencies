package objects.modelica;

import lombok.AllArgsConstructor;
import lombok.Getter;
import modelica.ModelicaPath;
import modelica.PathMatcher;
import modelica.pathresolvers.RelativePathResolver;
import objects.files.ModelicaFolder;

import java.util.List;

@AllArgsConstructor
public class Redeclaration implements
        IImportResolver,
        IDeclarationsResolver,
        IConstrainable,
        IRelativePathResolver,
        IModifiable {

    @Getter
    private String component;
    @Getter
    private String className;
    @Getter
    private String constrainingClass;

    @Override
    public boolean hasConstraint() {
        return constrainingClass != null;
    }

    @Override
    public IDeclarationsResolver resolveDeclaration(List<Declaration> declarations) {
        for (Declaration declaration : declarations) {
            if (className.equals(declaration.declarationName)) {
                String updatedConstrainingClass = constrainingClass;
                if (declaration.hasConstraint()) {
                    updatedConstrainingClass = declaration.constrainingClass;
                }
                return new RedeclarationBuilder()
                        .setComponent(component)
                        .setClassName(declaration.declarationClass)
                        .setConstrainingClass(updatedConstrainingClass)
                        .build();
            }
        }
        return this;
    }

    @Override
    public void resolveImport(String importedPath) {
        if (PathMatcher.isImportedPath(this.className, importedPath)) {
            className = ModelicaPath.joinSubPaths(importedPath, className);
        }
    }

    @Override
    public void modify(Modification modification) {

    }

    @Override
    public void resolveRelativePath(ModelicaFolder modelicaFile) {
        RelativePathResolver relativePathResolver = new RelativePathResolver(modelicaFile);
        className = relativePathResolver.resolvePath(className);
    }
}
