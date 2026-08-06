package io.write;

import objects.classes.ModelicaPackage;

import java.nio.file.Path;

public interface IWriter {
    void save(ModelicaPackage library, Path path);
}
