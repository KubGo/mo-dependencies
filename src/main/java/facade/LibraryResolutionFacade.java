package facade;

import files.ModelicaDirectoriesFinder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import modelica.classdefinitions.ClassDefinitionsResolver;
import modelica.packagestructure.PackageStructureResolver;
import objects.classes.ModelicaPackage;
import objects.files.ModelicaFolder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Facade to resolve library dependencies only based on the path to this library.
 * This Class abstracts and simplifies creating of the dependencies, so the user needs
 * to specify only path.
 */
@AllArgsConstructor
@NoArgsConstructor
public class LibraryResolutionFacade {

    private boolean resolveRelativePaths = true;
    private boolean resolveExtendingClasses = true;
    private boolean recursiveDirectoriesSearch = false;

    public ModelicaPackage resolveLibrary(String pathToLibrary) {
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

        return getModelicaLibrary(rootPath);
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
        if (resolveRelativePaths && resolveExtendingClasses) {
            modelicaLibrary.resolveExtendingClasses();
        }
        return modelicaLibrary;
    }
}
