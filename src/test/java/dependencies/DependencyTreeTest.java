package dependencies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DependencyTreeTest {

	DependencyTree tree;

	private final List<String> booleanDelayExampleClasses = Stream.of(
			"Buildings.Controls.Discrete.BooleanDelay",
			"Modelica.Blocks.Sources.BooleanPulse")
			.sorted().toList();
	private final List<String> conductorStepResponseClasses = Stream.of(
					"Buildings.HeatTransfer.Data.Solids.Concrete",
					"Buildings.HeatTransfer.Data.Resistances.Carpet",
					"Buildings.HeatTransfer.Data.OpaqueConstructions.Generic",
					"Buildings.HeatTransfer.Conduction.MultiLayer",
					"Buildings.HeatTransfer.Conduction.SingleLayer",
					"Buildings.HeatTransfer.Sources.FixedTemperature",
					"Buildings.HeatTransfer.Sources.PrescribedTemperature",
					"Modelica.Blocks.Sources.Step",
					"Modelica.Thermal.HeatTransfer.Sensors.HeatFlowSensor",
					"Modelica.Blocks.Math.Add",
					"Buildings.HeatTransfer.Convection.Interior")
			.sorted().toList();

	@BeforeEach
	void setUp() {
		tree = new DependencyTree();
	}

	@Test
	void getDependencyTree_BuildingsLibrary_correctDependenciesOfClasses(){
		String buildingLibraryPath = System.getenv("BuildingsLibraryPath");
		tree.generateLibraryDependencies(buildingLibraryPath, "Buildings");
		assertEquals(
				booleanDelayExampleClasses,
				tree.dependenciesMap.get("Buildings.Controls.Discrete.Examples.BooleanDelay")
						.getAbsolutePathsClassList().stream().sorted().toList()
					);
		assertEquals(
				conductorStepResponseClasses,
				tree.dependenciesMap.get("Buildings.HeatTransfer.Examples.ConductorStepResponse")
						.getAbsolutePathsClassList().stream().sorted().toList()
		            );
	}

}