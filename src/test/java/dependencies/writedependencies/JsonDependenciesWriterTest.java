package dependencies.writedependencies;

import dependencies.DependencyTreeResolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonDependenciesWriterTest {

	static DependencyTreeResolver dependencyTreeResolver;
	static String buildingLibraryPath;

	static JsonDependenciesWriter jsonDependenciesWriter = new JsonDependenciesWriter();

	@BeforeAll
	static void setUp() {
		dependencyTreeResolver = new DependencyTreeResolver();
		buildingLibraryPath = Utils.getPathAsString("BuildingsLite");
		dependencyTreeResolver.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
		jsonDependenciesWriter.setLibraryName("BuildingsLite");
		jsonDependenciesWriter.setPath(buildingLibraryPath);
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
			jsonDependenciesWriter.writeDependencies(dependencyTreeResolver.getSimplifiedClassDependencies());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}