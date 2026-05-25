package functionalities.coverage;

import dependencies.DependencyTreeResolver;
import dependencies.classesinfo.ClassDependencies;
import dependencies.readdependencies.JsonDependenciesReader;
import filtering.ModelicaLibraryFilter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCoverageTest {
	static Map<String, ClassDependencies> buildingsLiteDependencies;
	static Map<String, ClassDependencies> testDependencies;
	DependencyTreeResolver tree;
	ModelicaLibraryFilter filter = new ModelicaLibraryFilter("Modelica");

	@BeforeAll
	static void readBuildingsLiteDependencies() {
		JsonDependenciesReader reader = new JsonDependenciesReader(Utils.getPathAsString(Utils.BuildingsLite));
		try {
			buildingsLiteDependencies = reader.readDependencies();
			reader.setPath(Utils.getPathAsString(Utils.TestLibrary));
			testDependencies = reader.readDependencies();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void percentageCoverage_BuildingsLiteTests_getCorrectOutput() {
		TestCoverage testCoverage = new TestCoverage(buildingsLiteDependencies, testDependencies);
		assertEquals(0.98, testCoverage.getPercentageCoverage(), 0.01);
	}

}