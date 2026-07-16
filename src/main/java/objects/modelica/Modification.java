package objects.modelica;

import lombok.Getter;
import modelica.ModelicaPath;
import modelica.PathMatcher;

public class Modification implements IImportResolver {
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
}
