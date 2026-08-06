package io.write;

import facade.LibraryResolutionFacade;
import objects.classes.ModelicaPackage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.Utils.getPathAsString;

class JsonWriterTest {

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

    @Test
    void saveJson_buildingsLite_librarySavedCorrectly() {
        JsonWriter jsonWriter = new JsonWriter();
        Path pathToSave = Path.of("src/test/resources");
        jsonWriter.save(library, pathToSave);
        File file = Path.of(pathToSave.toString(), "BuildingsLite.json").toFile();
        assertTrue(
                file.exists()
        );
    }
}