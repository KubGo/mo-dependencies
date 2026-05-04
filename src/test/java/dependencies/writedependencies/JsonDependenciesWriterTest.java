package dependencies.writedependencies;

import dependencies.DependencyTree;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonDependenciesWriterTest {

	static DependencyTree tree;
	static String buildingLibraryPath;

	static JsonDependenciesWriter jsonDependenciesWriter = new JsonDependenciesWriter();

	@BeforeAll
	static void setUp() {
		tree = new DependencyTree();
		buildingLibraryPath = Utils.getPathAsString("BuildingsLite");
		tree.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
		jsonDependenciesWriter.setLibraryName("BuildingsLite");
		jsonDependenciesWriter.setPath(buildingLibraryPath);
	}

	@AfterAll
	static void cleanUp() {
		try {
			File file = new File(Path.of(buildingLibraryPath, "BuildingsLite_dependencies.json").toUri());
			if (file.exists()) file.delete();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void getFileName_BuildingsLite_correctFileName() {
		assertEquals("BuildingsLite_dependencies.json", jsonDependenciesWriter.getFileName());
	}

	@Test
	void getFileName_BuildingsLite_correctFileNameWithDifferentExtension() {
		jsonDependenciesWriter.setSuffix("_dep");
		assertEquals("BuildingsLite_dep.json", jsonDependenciesWriter.getFileName());
	}


	@Test
	void writeDependencies_BuildingsLite_dependenciesSavedCorrectly() {
		try {
			jsonDependenciesWriter.writeDependencies(tree);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}