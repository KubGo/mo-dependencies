package functionalities.tests;

import dependencies.DependencyTreeResolver;
import dependencies.classesinfo.ClassDependencies;
import dependencies.readdependencies.JsonDependenciesReader;
import filtering.ModelicaLibraryFilter;
import functionalities.affectedclasses.AffectedClassesResolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCasesResolverTest {

	static Map<String, ClassDependencies> buildingsLiteDependencies;
	DependencyTreeResolver tree;
	ModelicaLibraryFilter filter = new ModelicaLibraryFilter("Modelica");

	@BeforeAll
	static void readBuildingsLiteDependencies() {
		JsonDependenciesReader reader = new JsonDependenciesReader(Utils.getPathAsString(Utils.BuildingsLite));
		try {
			buildingsLiteDependencies = reader.readDependencies();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@BeforeEach
	void setup() {
		tree = new DependencyTreeResolver(List.of(filter));
	}

	@Test
	void getTestCasesToRun_TestLibrary_checkWithoutDependenciesFromBuildingsLite() {
		tree.generateLibraryDependencies("src/test/resources/TestLibrary", "TestLibrary");
		TestCasesResolver testCasesResolver = new TestCasesResolver(tree.getDependencyTree());
		List<String> results = testCasesResolver.getTestCasesToRun(List.of("BuildingsLite.Tests.BouncingBall"));
		assertEquals(List.of("TestLibrary.Tests.BouncingBallTest"), results);
		results = testCasesResolver.getTestCasesToRun(List.of(
				"BuildingsLite.Tests.BouncingBall", "BuildingsLite.HeatTransfer.Convection.Interior",
				"BuildingsLite.HeatTransfer.Convection.Exterior"));
		assertEquals(
				Stream.of("TestLibrary.HeatTransfer.InteriorConvectionTest", "TestLibrary.Tests.BouncingBallTest")
						.sorted()
						.toList(), results.stream().sorted().toList());
	}

	@Test
	void getTestCasesToRun_TestLibrary_checkWithoutDependenciesFromBuildingsLiteMultipleChanges() {
		tree.generateLibraryDependencies("src/test/resources/TestLibrary", "TestLibrary");
		TestCasesResolver testCasesResolver = new TestCasesResolver(tree.getDependencyTree());
		List<String> results = testCasesResolver.getTestCasesToRun(List.of(
				"BuildingsLite.Tests.BouncingBall", "BuildingsLite.HeatTransfer.Convection.Interior",
				"BuildingsLite.HeatTransfer.Convection.Exterior"));
		assertEquals(
				Stream.of("TestLibrary.HeatTransfer.InteriorConvectionTest", "TestLibrary.Tests.BouncingBallTest")
						.sorted()
						.toList(), results.stream().sorted().toList());
	}

	@Test
	void getTestCasesToRun_TestLibrary_checkWithDependenciesFromBuildingsLiteMultipleChanges() {
		tree.addLibraryDependencies(buildingsLiteDependencies);
		tree.generateLibraryDependencies("src/test/resources/TestLibrary", "TestLibrary");
		TestCasesResolver testCasesResolver = new TestCasesResolver(tree.getDependencyTree());
		List<String> results = testCasesResolver.getTestCasesToRun(List.of(
				"BuildingsLite.Tests.BouncingBall", "BuildingsLite.HeatTransfer.Convection.Interior",
				"BuildingsLite.HeatTransfer.Convection.Exterior"));
		assertEquals(
				Stream.of(
						"TestLibrary.HeatTransfer.InteriorConvectionTest", "TestLibrary.Tests.BouncingBallTest",
						"TestLibrary.HeatTransfer.ExteriorConvectionTest").sorted().toList(),
				results.stream().sorted().toList());
	}

	@Test
	void getTestCasesToRun_TestLibrary_extendedClassesAreDiscovered() {
		tree.addLibraryDependencies(buildingsLiteDependencies);
		tree.generateLibraryDependencies("src/test/resources/TestLibrary", "TestLibrary");
		TestCasesResolver testCasesResolver = new TestCasesResolver(tree.getDependencyTree());
		AffectedClassesResolver<ClassDependencies> resolver = new AffectedClassesResolver<>(buildingsLiteDependencies);
		resolver.setIncludeChildrenClasses(true);
		List<String> affectedClasses = resolver.getAffectedClasses("BuildingsLite.Tests.PartialSimpleModel");
		List<String> results = testCasesResolver.getTestCasesToRun(affectedClasses);

		assertEquals(
				Stream.of("TestLibrary.Tests.SimpleModelTest", "TestLibrary.Tests.MutlipleExtendModelTest")
						.sorted()
						.toList(), results.stream().sorted().toList());
	}

	@Test
	void getTestCasesToRun_TestLibrary_noPartialModelsIncluded() {
		tree.generateLibraryDependencies("src/test/resources/TestLibrary", "TestLibrary");
		TestCasesResolver testCasesResolver = new TestCasesResolver(tree.getDependencyTree());
		List<String> results = testCasesResolver.getTestCasesToRun(
				List.of("BuildingsLite.HeatTransfer.Radiosity.OpaqueSurface"));
		assertEquals(List.of("TestLibrary.Tests.RadiocityTestCase"), results);
	}

}