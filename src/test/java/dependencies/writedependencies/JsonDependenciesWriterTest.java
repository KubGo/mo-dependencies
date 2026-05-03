package dependencies.writedependencies;

import dependencies.DependencyTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonDependenciesWriterTest {

	DependencyTree tree;

	JsonDependenciesWriter jsonDependenciesWriter = new JsonDependenciesWriter();

	@BeforeEach
	void setUp() {
		tree = new DependencyTree();
		String buildingLibraryPath = Utils.getPathAsString("BuildingsLite");
		tree.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
		jsonDependenciesWriter.setLibraryName("BuildingsLite");
		jsonDependenciesWriter.setPath(buildingLibraryPath);
	}

	@Test
	void getFileName_BuildingsLite_correctFileName() {
		assertEquals("BuildingsLite_dependencies.json", jsonDependenciesWriter.getFileName());
	}

	@Test
	void writeDependencies_BuildingsLite_dependenciesSavedCorrectly() {
		jsonDependenciesWriter.writeDependencies(tree);
	}
}