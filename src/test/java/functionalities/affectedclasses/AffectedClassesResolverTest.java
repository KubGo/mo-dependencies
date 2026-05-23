package functionalities.affectedclasses;

import dependencies.DependencyTreeResolver;
import dependencies.classesinfo.ClassDependencies;
import dependencies.readdependencies.JsonDependenciesReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AffectedClassesResolverTest {

	static DependencyTreeResolver tree;
	static String buildingLibraryPath;
	static AffectedClassesResolver affectedClassesResolver;

	private final List<String> bouncingBallAffectedClasses = Stream.of(
			"BuildingsLite.Tests.BouncingBall", "BuildingsLite.Tests.SimpleModel",
			"BuildingsLite.Tests.MultipleExtendsModel").sorted().toList();
	private final List<String> dayTypeAffectedClasses = Stream.of(
			"BuildingsLite.Controls.Sources.DayType", "BuildingsLite.Controls.DemandResponse.Examples.ClientLBNL90",
			"BuildingsLite.Controls.Predictors.Examples.BESTEST",
			"BuildingsLite.Controls.Predictors.Validation.BaseClasses.PartialSimpleTestCase",
			"BuildingsLite.Controls.Predictors.Validation.ConstantInput",
			"BuildingsLite.Controls.Predictors.Validation.ConstantInputDayOfAdjustment",
			"BuildingsLite.Controls.Predictors.Validation.LinearInput",
			"BuildingsLite.Controls.Predictors.Validation.LinearInputDayOfAdjustment",
			"BuildingsLite.Controls.Predictors.Validation.SineInput",
			"BuildingsLite.Controls.Predictors.Validation.SineInputDayOfAdjustment",
			"BuildingsLite.Controls.Sources.Examples.DayType").sorted().toList();


	@BeforeAll
	static void setUp() {
		tree = new DependencyTreeResolver();
		buildingLibraryPath = Utils.getPathAsString("BuildingsLite");
		tree.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
		affectedClassesResolver = new AffectedClassesResolver(tree.getDependencyTree());
	}

	@Test
	void getAffectedClasses_BouncingBall_classesMatch() {
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(
				List.of("BuildingsLite.Tests.BouncingBall"));
		assertEquals(bouncingBallAffectedClasses, affectedClasses.stream().sorted().toList());
	}

	@Test
	void getAffectedClasses_DayType_classesMatch() {
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(
				List.of("BuildingsLite.Controls.Sources.DayType"));
		assertEquals(
				String.join("\n", dayTypeAffectedClasses),
				String.join("\n", affectedClasses.stream().sorted().toList()));
	}

	@Test
	void getAffectedClasses_DayTypeAndBouncingBall_classesMatch() {
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(
				List.of("BuildingsLite.Controls.Sources.DayType", "BuildingsLite.Tests.BouncingBall"));
		List<String> expected = new ArrayList<>(dayTypeAffectedClasses);
		expected.addAll(bouncingBallAffectedClasses);
		expected = expected.stream().sorted().toList();
		assertEquals(String.join("\n", expected), String.join("\n", affectedClasses.stream().sorted().toList()));
	}

	@Test
	void getAffectedClasses_ModelNotUsedInLibrary_emptyList() {
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(List.of("Library.Name.Model"));
		assertEquals(List.of("Library.Name.Model"), affectedClasses);
	}

	@Test
	void getAffectedClasses_DayTypeAndBouncingBall_readingFromSavedFile() {
		try {
			Map<String, ClassDependencies> dependenciesTree = new JsonDependenciesReader(
					Utils.getPathAsString(Utils.BuildingsLite)).readDependencies();
			AffectedClassesResolver<ClassDependencies> affectedClassesResolverFromRead = new AffectedClassesResolver<>(
					dependenciesTree);
			List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(
					List.of("BuildingsLite.Controls.Sources.DayType", "BuildingsLite.Tests.BouncingBall"));
			List<String> expected = new ArrayList<>(dayTypeAffectedClasses);
			expected.addAll(bouncingBallAffectedClasses);
			expected = expected.stream().sorted().toList();
			assertEquals(String.join("\n", expected), String.join("\n", affectedClasses.stream().sorted().toList()));
		} catch (RuntimeException | FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void getAffectedClasses_PartialSimpleModel_getAffectedClassesNoChildrenIncluded() {
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(
				List.of("BuildingsLite.Tests.PartialSimpleModel"));
		assertEquals(
				String.join(
						"\n", Stream.of("BuildingsLite.Tests.PartialSimpleModel", "BuildingsLite.Tests.SimpleModel")
								.sorted()
								.toList()), String.join("\n", affectedClasses.stream().sorted().toList()));
	}

	@Test
	void getAffectedClasses_PartialSimpleModel_getAffectedClassesChildrenIncluded() {
		affectedClassesResolver.setIncludeChildrenClasses(true);
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(
				List.of("BuildingsLite.Tests.PartialSimpleModel"));
		assertEquals(
				String.join(
						"\n", Stream.of(
								"BuildingsLite.Tests.PartialSimpleModel", "BuildingsLite.Tests.SimpleModel",
								"BuildingsLite.Tests.MultipleExtendsModel").sorted().toList()),
				String.join("\n", affectedClasses.stream().sorted().toList()));
	}
}