package dependencies;

import dependencies.writedependencies.JsonDependenciesWriter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DependencyTreeResolverTest {

	static DependencyTreeResolver tree;

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
					"Modelica.Blocks.Math.Add", "BuildingsLite.HeatTransfer.Convection.Interior",
					"BuildingsLite.Types.Tilt.Wall")
			.sorted().toList();
	private final List<String> simpleExampleClasses = Stream.of(
			"BuildingsLite.HeatTransfer.Conduction.SingleLayer", "BuildingsLite.Controls.Discrete.BooleanDelay",
			"BuildingsLite.Airflow.Multizone.MediumColumn", "BuildingsLite.Tests.BouncingBall",
			"BuildingsLite.HeatTransfer.Data.Solids.Concrete", "Modelica.Units.SI.Length", "Modelica.Units.SI.Area",
			"BuildingsLite.HeatTransfer.Radiosity.Constant",
			"BuildingsLite.Airflow.Multizone.Types.densitySelection.fromTop",
			"OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction").sorted().toList();

	private final List<String> reverseBuoyancy3ZonesClasses = Stream.of(
			"BuildingsLite.Media.Air",
			"BuildingsLite.Fluid.MixingVolumes.MixingVolume", "BuildingsLite.Airflow.Multizone.Orifice",
			"BuildingsLite.Airflow.Multizone.MediumColumn", "BuildingsLite.Airflow.Multizone.DoorDiscretizedOperable",
			"Modelica.Blocks.Sources.Constant", "BuildingsLite.Airflow.Multizone.Types.densitySelection.fromTop",
			"BuildingsLite.Airflow.Multizone.Types.densitySelection.fromBottom",
			"Modelica.Fluid.Types.Dynamics.FixedInitial").sorted().toList();

	@BeforeAll
	static void setUp() {
		tree = new DependencyTreeResolver();
		String buildingLibraryPath = Utils.getPathAsString(Utils.BuildingsLite);
		tree.generateLibraryDependencies(buildingLibraryPath);
	}

	@Test
	void getDependencyTree_BuildingsLiteLibrary_correctDependenciesOfClasses() {
		assertEquals(
				booleanDelayExampleClasses,
				tree.getDependencyTree().get("BuildingsLite.Controls.Discrete.Examples.BooleanDelay")
						.getClasses().stream().sorted().toList()
					);
		assertEquals(
				String.join("\n", conductorStepResponseClasses),
				String.join("\n", tree.getDependencyTree().get("BuildingsLite.HeatTransfer.Examples.ConductorStepResponse")
						.getClasses().stream().sorted().toList())
		            );
	}

	@Test
	void getDependencyTree_SimpleModel_correctAbsolutePathsFromLibrary() {
		assertEquals(
				String.join("\n", simpleExampleClasses), String.join(
						"\n", tree.getDependencyTree().get("BuildingsLite.Tests.SimpleModel")
								.getClasses()
								.stream()
								.sorted()
								.toList()));
	}

	@Test
	void getDependencyTree_ReverseBuoyancy3Zones_correctAbsolutePathsFromLibrary() {
		assertEquals(
				String.join("\n", reverseBuoyancy3ZonesClasses), String.join(
						"\n", tree.getDependencyTree()
								.get("BuildingsLite.Airflow.Multizone.Examples.ReverseBuoyancy3Zones")
								.getClasses()
								.stream()
								.sorted()
								.toList()));
	}

	@Test
	void getDependencyTree_FunctionsRedeclaration_correctRedeclarationOfFunction() {
		assertEquals(
				Stream.of("Modelica.Blocks.Interfaces.RealInput", "Modelica.Blocks.Interfaces.RealOutput", "sum")
						.sorted()
						.toList(), tree.getClassDependencies("BuildingsLite.Bugfixes.PartialCalculation")
						.getClasses()
						.stream()
						.sorted()
						.toList());

//		assertEquals(
//				Stream.of(
//						"Modelica.Blocks.Interfaces.RealInput",
//						"Modelica.Blocks.Interfaces.RealOutput",
//						"sum"
//				         ).sorted().toList(),
//				tree.getClassDependencies("BuildingsLite.Bugfixes.Sum")
//						.getClasses()
//						.stream()
//						.sorted()
//						.toList());

//		assertEquals(
//				Stream.of(
//						"Modelica.Blocks.Interfaces.RealInput",
//						"Modelica.Blocks.Interfaces.RealOutput",
//						"sum"
//				         ).sorted().toList(),
//				tree.getClassDependencies("BuildingsLite.Bugfixes.PartialCalculation")
//						.getComponentDeclarations());
//
//		assertEquals(
//				Stream.of(
//						"Modelica.Blocks.Interfaces.RealInput",
//						"Modelica.Blocks.Interfaces.RealOutput",
//						"difference"
//				         ).sorted().toList(),
//				tree.getClassDependencies("BuildingsLite.Bugfixes.Difference")
//						.getClasses()
//						.stream()
//						.sorted()
//						.toList());

	}

	@Test
	void saveDependencyTree_BuildingsLite_allWritersWork() {
		JsonDependenciesWriter jsonDependenciesWriter = new JsonDependenciesWriter();
		tree.saveDependencies(jsonDependenciesWriter);
	}
}