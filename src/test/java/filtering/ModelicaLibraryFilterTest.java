package filtering;

import dependencies.DependencyTreeResolver;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaLibraryFilterTest {

	private final List<String> booleanDelayExampleClasses = List.of("BuildingsLite.Controls.Discrete.BooleanDelay");
	private final List<String> conductorStepResponseClasses = Stream.of(
			"BuildingsLite.HeatTransfer.Data.Solids.Concrete", "BuildingsLite.HeatTransfer.Data.Resistances.Carpet",
			"BuildingsLite.HeatTransfer.Data.OpaqueConstructions.Generic",
			"BuildingsLite.HeatTransfer.Conduction.MultiLayer", "BuildingsLite.HeatTransfer.Conduction.SingleLayer",
			"BuildingsLite.HeatTransfer.Sources.FixedTemperature",
			"BuildingsLite.HeatTransfer.Sources.PrescribedTemperature",
			"BuildingsLite.HeatTransfer.Convection.Interior").sorted().toList();
	ModelicaLibraryFilter filter = new ModelicaLibraryFilter("Modelica");

	@Test
	void filterClass_classShouldBeUsed_returnsTrue() {
		assertTrue(filter.filterName("Library.package.model"));
	}

	@Test
	void filterClass_pathSplittingEdgeCase_returnsTrue() {
		assertTrue(filter.filterName("Library"));
	}

	@Test
	void filterClass_classShouldNotBeUsed_returnsFalse() {
		assertFalse(filter.filterName("Modelica.package.model"));
	}

	@Test
	void filterClass_pathSplittingEdgeCase_returnsFalse() {
		assertFalse(filter.filterName("Modelica"));
	}

	@Test
	void filterClass_DependencyTree_returnClassesWithoutMSL() {
		DependencyTreeResolver tree = new DependencyTreeResolver(filter);
		String buildingLibraryPath = Utils.getPathAsString(Utils.BuildingsLite);
		tree.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
		assertEquals(
				booleanDelayExampleClasses,
				tree.getClassDependencies("BuildingsLite.Controls.Discrete.Examples.BooleanDelay")
						.getClasses()
						.stream()
						.sorted()
						.toList());
		assertEquals(
				conductorStepResponseClasses,
				tree.getClassDependencies("BuildingsLite.HeatTransfer.Examples.ConductorStepResponse")
						.getClasses()
						.stream()
						.sorted()
						.toList());
	}
}