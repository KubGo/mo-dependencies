package io.read;

import facade.LibraryResolutionFacade;
import io.write.JsonPackageWriter;
import objects.classes.ModelicaPackage;
import objects.modelica.Component;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.getPathAsString;

class JsonPackageReaderTest {

    static ModelicaPackage library;
    static Path savedPath;
    static JsonPackageReader packageReader = new JsonPackageReader();

    @BeforeAll
    static void setup() {
        LibraryResolutionFacade facade = new LibraryResolutionFacade(
                true,
                true,
                true
        );
        library = facade.resolveLibraries(
                getPathAsString(Utils.BuildingsLite)
        ).getFirst();
        JsonPackageWriter jsonPackageWriter = new JsonPackageWriter();
        Path pathToSave = Path.of("src/test/resources");
        jsonPackageWriter.save(library, pathToSave);
        File file = Path.of(pathToSave.toString(), "BuildingsLite.json").toFile();
        savedPath = file.toPath();
    }

    @AfterAll
    static void cleanUp() {
        Path savedPath = Path.of("src/test/resources");
        File file = Path.of(savedPath.toString(), "BuildingsLite.json").toFile();
        boolean deleted = file.delete();
        if (!deleted) {
            throw new RuntimeException("Cleanup failed. Couldn't delete " + savedPath);
        }
    }

    @Test
    void fileReadable_buildingsLite_fileCanBeRead() {
        assertTrue(
                packageReader.fileReadable(savedPath)
        );
    }

    @Test
    void fileReadable_wrongPath_fileCannotBeRead() {
        Path directoryPath = Path.of("src/test/resources");
        Path wrongPath = Path.of("src/test/nonexistingfodler/BuildingLite.json");
        Path notJsonPath = Path.of("src/test/BuildingsLite/Airflow/package.mo");
        assertFalse(
                packageReader.fileReadable(directoryPath)
        );
        assertFalse(
                packageReader.fileReadable(wrongPath)
        );
        assertFalse(
                packageReader.fileReadable(notJsonPath)
        );
    }

    @Test
    void readPackage_buildingsLite_readCorrectly() {
        if (packageReader.fileReadable(savedPath)) {
            ModelicaPackage readLibrary = packageReader.read(savedPath);
            assertEquals(
                    library.getClassName(),
                    readLibrary.getClassName()
            );
            String className = "BuildingsLite.Tests.ComplexExample";
            assertEquals(
                    library.getByName(className)
                            .getComponents()
                            .stream()
                            .map(Component::getClassName)
                            .sorted().toList(),
                    readLibrary.getByName(className)
                            .getComponents()
                            .stream()
                            .map(Component::getClassName)
                            .sorted().toList()
            );
            className = "BuildingsLite.Bugfixes.Difference";
            assertEquals(
                    library.getByName(className)
                            .getComponents()
                            .stream()
                            .map(Component::getClassName)
                            .sorted().toList(),
                    readLibrary.getByName(className)
                            .getComponents()
                            .stream()
                            .map(Component::getClassName)
                            .sorted().toList()
            );
        }
    }

}