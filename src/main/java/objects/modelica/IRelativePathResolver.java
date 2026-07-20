package objects.modelica;

import objects.files.IModelicaFile;

public interface IRelativePathResolver {
    void resolveRelativePath(IModelicaFile modelicaFile);
}
