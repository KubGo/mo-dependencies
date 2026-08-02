package files;

import lombok.Getter;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModelicaDirectoriesFinder {
    @Getter
    private final List<Path> librariesPaths = new ArrayList<>();
    private final Path path;
    private boolean recursiveFind = false;


    public ModelicaDirectoriesFinder(Path path, boolean recursiveFind) {
        this.path = path;
        this.recursiveFind = recursiveFind;
        File file = path.toFile();
        getModelicaPaths(file);
    }

    public ModelicaDirectoriesFinder(Path path) {
        this(path, false);
    }

    public boolean modelicaDirectoriesFound() {
        return !librariesPaths.isEmpty();
    }

    private void getModelicaPaths(File file) {
        if (isModelicaFolder(file)) {
            librariesPaths.add(file.toPath().toAbsolutePath());
        } else if (this.recursiveFind) {
            var files = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .filter(File::isDirectory)
                    .toList();
            for (File listFile : files) {
                getModelicaPaths(listFile);
            }
        }
    }

    private boolean isModelicaFolder(File file) {
        if (!file.isDirectory()) {
            return file.getName().endsWith(".mo");
        } else {
            List<File> modelicaFiles = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .filter(File::isFile)
                    .filter(this::isModelicaFile)
                    .toList();
            return !modelicaFiles.isEmpty();
        }
    }

    private boolean isModelicaFile(File file) {
        String name = file.getName();
        return name.endsWith(".mo") || name.endsWith(".order");
    }

}
