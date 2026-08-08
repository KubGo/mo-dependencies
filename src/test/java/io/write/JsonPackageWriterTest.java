package io.write;

import facade.LibraryResolutionFacade;
import objects.classes.ModelicaPackage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Utils.getPathAsString;

class JsonPackageWriterTest {

    static ModelicaPackage library;

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
    void saveJson_buildingsLite_librarySavedCorrectly() {
        JsonPackageWriter jsonPackageWriter = new JsonPackageWriter();
        Path pathToSave = Path.of("src/test/resources");
        jsonPackageWriter.save(library, pathToSave);
        File file = Path.of(pathToSave.toString(), "BuildingsLite.json").toFile();
        assertTrue(
                file.exists()
        );
    }
}