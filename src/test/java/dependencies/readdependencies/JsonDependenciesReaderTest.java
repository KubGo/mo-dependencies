package dependencies.readdependencies;

import dependencies.DependencyTree;
import dependencies.classesinfo.ClassDependencies;
import dependencies.writedependencies.JsonDependenciesWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonDependenciesReaderTest {

    static DependencyTree tree;

    static JsonDependenciesWriter jsonDependenciesWriter = new JsonDependenciesWriter();
    JsonDependenciesReader jsonDependenciesReader;

    @BeforeAll
    static void setUp(){
        tree = new DependencyTree();
        String buildingLibraryPath = Utils.getPathAsString(Utils.BuildingsLite);
        tree.generateLibraryDependencies(buildingLibraryPath, Utils.BuildingsLite);
        jsonDependenciesWriter.setLibraryName("BuildingsLite");
        jsonDependenciesWriter.setPath(buildingLibraryPath);
        try {
            jsonDependenciesWriter.writeDependencies(tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkExtension_BuildingsLibrary_correctExtensionPathToPackage(){
        jsonDependenciesReader = new JsonDependenciesReader(Utils.getPathAsString(Utils.BuildingsLite));
        assertTrue(jsonDependenciesReader.checkExtension());
    }

    @Test
    void checkExtension_BuildingsLibrary_correctExtensionPathToDependenciesFile(){
        Path pathToFile = Path.of(Utils.getPathAsString(Utils.BuildingsLite), "BuildingsLite_dependencies.json");
        jsonDependenciesReader = new JsonDependenciesReader(pathToFile.toAbsolutePath().toString());
        assertTrue(jsonDependenciesReader.checkExtension());
    }

    @Test
    void readDependencies_BuildingsLite_verifyDependenciesMatch() {
        jsonDependenciesReader = new JsonDependenciesReader(Utils.getPathAsString(Utils.BuildingsLite));
        List<ClassDependencies> dependencies = jsonDependenciesReader.readDependencies();
        tree.reset();
        int i = 0;
        while (tree.hasNextClassDependencies()) {
            assertEquals(tree.getNextClassDependencies().toString(), dependencies.get(i).toString());
            i++;
        }
    }

    @Test
    void readDependencies_BuildingsLite_throwsExceptionWhenPathNotFound() throws RuntimeException {
        jsonDependenciesReader = new JsonDependenciesReader(Utils.getPathAsString(Utils.BuildingsLite), "_dep");
        assertThrows(RuntimeException.class, () -> jsonDependenciesReader.readDependencies());
    }

}