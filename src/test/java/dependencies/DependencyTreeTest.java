package dependencies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DependencyTreeTest {

	DependencyTree tree;

	private final List<String> booleanDelayExampleClasses = Stream.of(
			"BuildingsLite.Controls.Discrete.BooleanDelay",
			"Modelica.Blocks.Sources.BooleanPulse")
			.sorted().toList();
	private final List<String> conductorStepResponseClasses = Stream.of(
					"BuildingsLite.HeatTransfer.Data.Solids.Concrete",
					"BuildingsLite.HeatTransfer.Data.Resistances.Carpet",
					"BuildingsLite.HeatTransfer.Data.OpaqueConstructions.Generic",
					"BuildingsLite.HeatTransfer.Conduction.MultiLayer",
					"BuildingsLite.HeatTransfer.Conduction.SingleLayer",
					"BuildingsLite.HeatTransfer.Sources.FixedTemperature",
					"BuildingsLite.HeatTransfer.Sources.PrescribedTemperature",
					"Modelica.Blocks.Sources.Step",
					"Modelica.Thermal.HeatTransfer.Sensors.HeatFlowSensor",
					"Modelica.Blocks.Math.Add",
					"BuildingsLite.HeatTransfer.Convection.Interior")
			.sorted().toList();

	@BeforeEach
	void setUp() {
		tree = new DependencyTree();
	}

	@Test
	void getDependencyTree_BuildingsLibrary_correctDependenciesOfClasses(){
		String buildingLibraryPath = Utils.getPathAsString("BuildingsLite");
		tree.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
		assertEquals(
				booleanDelayExampleClasses,
				tree.dependenciesMap.get("BuildingsLite.Controls.Discrete.Examples.BooleanDelay")
						.getAbsolutePathsClassList().stream().sorted().toList()
					);
		assertEquals(
				conductorStepResponseClasses,
				tree.dependenciesMap.get("BuildingsLite.HeatTransfer.Examples.ConductorStepResponse")
						.getAbsolutePathsClassList().stream().sorted().toList()
		            );
	}

}