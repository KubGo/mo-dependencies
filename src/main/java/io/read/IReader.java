package io.read;

import objects.classes.ModelicaPackage;

import java.nio.file.Path;

public interface IReader {
    ModelicaPackage read(Path path);

    boolean fileReadable(Path path);
}
