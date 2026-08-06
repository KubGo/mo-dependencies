package facade;

import files.ModelicaDirectoriesFinder;
import lombok.NoArgsConstructor;
import modelica.classdefinitions.ClassDefinitionsResolver;
import modelica.packagestructure.PackageStructureResolver;
import objects.classes.ModelicaPackage;
import objects.files.ModelicaFolder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade to resolve library dependencies only based on the path to this library.
 * This Class abstracts and simplifies creating of the dependencies, so the user needs
 * to specify only path.
 */
@NoArgsConstructor
public class LibraryResolutionFacade {

    private final List<ModelicaPackage> resolvedLibraries = new ArrayList<>();
    private boolean resolveRelativePaths = true;
    private boolean resolveExtendingClasses = true;
    private boolean recursiveDirectoriesSearch = false;
    // TODO("Add filtering of libraries, e.g. Modelica")

    public LibraryResolutionFacade(boolean resolveRelativePaths, boolean resolveExtendingClasses, boolean recursiveDirectoriesSearch) {
        this.resolveRelativePaths = resolveRelativePaths;
        this.resolveExtendingClasses = resolveExtendingClasses;
        this.recursiveDirectoriesSearch = recursiveDirectoriesSearch;
    }

    public List<ModelicaPackage> resolveLibraries(String pathToLibrary) {
        Path rootPath = Paths.get(pathToLibrary);
        ModelicaDirectoriesFinder modelicaDirectoriesFinder = new ModelicaDirectoriesFinder(rootPath, recursiveDirectoriesSearch);
        if (Files.notExists(rootPath)) {
            throw new RuntimeException("Path: ' " + pathToLibrary + "' doesn't exist");
        }
        if (!modelicaDirectoriesFinder.modelicaDirectoriesFound()) {
            if (recursiveDirectoriesSearch) {
                throw new RuntimeException("Could not find any Modelica libraries below path: " + pathToLibrary + ".");
            } else {
                throw new RuntimeException("There are no Modelica files under path: " + pathToLibrary + ".");
            }
        }
        List<ModelicaPackage> modelicaPackages = modelicaDirectoriesFinder
                .getLibrariesPaths()
                .stream().map(this::getModelicaLibrary)
                .toList();

        resolvedLibraries.addAll(modelicaPackages);

        if (resolveRelativePaths && resolveExtendingClasses) {
            resolveExtends(modelicaPackages);
        }

        return resolvedLibraries;
    }

    private void resolveExtends(List<ModelicaPackage> modelicaPackages) {
        for (var library : resolvedLibraries) {
            for (var modelicaPackage : modelicaPackages) {
                library.setExportsResolved(false);
                library.resolveExtendingClasses(modelicaPackage);
            }
        }
    }

    private ModelicaPackage getModelicaLibrary(Path rootPath) {
        PackageStructureResolver packageStructureResolver = new PackageStructureResolver(
                rootPath.toString()
        );
        ModelicaFolder modelicaFolder = packageStructureResolver.getLibraryPackage();
        ClassDefinitionsResolver classDefinitionsResolver = new ClassDefinitionsResolver(modelicaFolder);
        ModelicaPackage modelicaLibrary = classDefinitionsResolver.generateClassDefinitions();

        if (resolveRelativePaths) {
            modelicaLibrary.resolveRelativePaths(modelicaFolder);
        }
        return modelicaLibrary;
    }
}
