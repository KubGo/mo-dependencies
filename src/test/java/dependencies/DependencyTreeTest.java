package dependencies;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DependencyTreeTest {

	static DependencyTree tree;

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
	private final List<String> simpleExampleClasses = Stream.of(
			"BuildingsLite.HeatTransfer.Conduction.SingleLayer", "BuildingsLite.Controls.Discrete.BooleanDelay",
			"BuildingsLite.Airflow.Multizone.MediumColumn", "BuildingsLite.Tests.BouncingBall",
			"BuildingsLite.HeatTransfer.Data.Solids.Concrete",
			"OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction").sorted().toList();

	private final List<String> reverseBuoyancy3ZonesClasses = Stream.of(
//			"BuildingsLite.Media.Air", TODO(Consider definition of classes inside of class)
			"BuildingsLite.Fluid.MixingVolumes.MixingVolume", "BuildingsLite.Airflow.Multizone.Orifice",
			"BuildingsLite.Airflow.Multizone.MediumColumn", "BuildingsLite.Airflow.Multizone.DoorDiscretizedOperable",
			"Modelica.Blocks.Sources.Constant"
//			"Modelica.Fluid.Types.Dynamics.FixedInitial" TODO(Consider redefinitions and assignments)
	                                                                   ).sorted().toList();

	@BeforeAll
	static void setUp() {
		tree = new DependencyTree();
		String buildingLibraryPath = Utils.getPathAsString("BuildingsLite");
		tree.generateLibraryDependencies(buildingLibraryPath, "BuildingsLite");
	}

	@Test
	void getDependencyTree_BuildingsLiteLibrary_correctDependenciesOfClasses() {
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

	@Test
	void getDependencyTree_SimpleModel_correctAbsolutePathsFromLibrary() {
		assertEquals(
				String.join("\n", simpleExampleClasses), String.join(
						"\n", tree.dependenciesMap.get("BuildingsLite.Tests.SimpleModel")
								.getAbsolutePathsClassList()
								.stream()
								.sorted()
								.toList()));
	}

	@Test
	void getDependencyTree_ReverseBuoyancy3Zones_correctAbsolutePathsFromLibrary() {
		assertEquals(
				String.join("\n", reverseBuoyancy3ZonesClasses), String.join(
						"\n", tree.dependenciesMap.get("BuildingsLite.Airflow.Multizone.Examples.ReverseBuoyancy3Zones")
								.getAbsolutePathsClassList()
								.stream()
								.sorted()
								.toList()));
	}
}