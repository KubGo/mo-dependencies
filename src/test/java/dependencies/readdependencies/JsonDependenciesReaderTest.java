package dependencies.readdependencies;

import dependencies.DependencyTree;
import dependencies.writedependencies.JsonDependenciesWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Path;

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

}