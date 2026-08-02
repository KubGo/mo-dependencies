package files;

import org.junit.jupiter.api.Test;
import utils.Utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaDirectoriesFinderTest {

    ModelicaDirectoriesFinder modelicaDirectoriesFinder;

    @Test
    void foundModelicaDirectory_pathToLibrary_correctlyFound() {
        String pathString = Utils.getPathAsString(Utils.BuildingsLite);
        Path path = Path.of(pathString);
        modelicaDirectoriesFinder = new ModelicaDirectoriesFinder(path, false);
        assertTrue(
                modelicaDirectoriesFinder.modelicaDirectoriesFound()
        );
        assertEquals(
                1,
                modelicaDirectoriesFinder.getLibrariesPaths().size()
        );
    }

    @Test
    void notFoundModelicaDirectory_pathToLibrary_correctlyNotFound() {
        Path path = Paths.get("/src/test/java/utils");
        modelicaDirectoriesFinder = new ModelicaDirectoriesFinder(path, false);
        assertFalse(
                modelicaDirectoriesFinder.modelicaDirectoriesFound()
        );
    }

    @Test
    void recursiveSearch_pathToMultipleResources_correctlyFound() {
        Path path = Paths.get("src/test/resources");
        modelicaDirectoriesFinder = new ModelicaDirectoriesFinder(path, true);
        assertTrue(
                modelicaDirectoriesFinder.modelicaDirectoriesFound()
        );
        assertEquals(
                2,
                modelicaDirectoriesFinder.getLibrariesPaths().size()
        );
    }

    @Test
    void notRecursiveSearch_pathToMultipleResources_correctlyNotFound() {
        Path path = Paths.get("src/test/resources");
        modelicaDirectoriesFinder = new ModelicaDirectoriesFinder(path, false);
        assertFalse(
                modelicaDirectoriesFinder.modelicaDirectoriesFound()
        );
    }
}