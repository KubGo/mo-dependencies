package dependencies.classesinfo;

import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassDependenciesResolverTest {


	@Test
	void getDependencies_BouncingBall_correctClasses() throws IOException {
		String classText = Utils.getModelicaTextFromResources(Utils.BouncingBall);

		ClassDependenciesResolver classDependenciesResolver = new ClassDependenciesResolver("BouncingBall", classText);
		assertEquals(3, classDependenciesResolver.getClasses().size());
	}

	@Test
	void resolveInternalDependencies_ImportsTest_importedClassesAbsolutePathsMatch() throws IOException {
		String classText = Utils.getModelicaTextFromResources(Utils.ImportsTest);
		ClassDependenciesResolver classDependenciesResolver = new ClassDependenciesResolver("ImportsTest", classText);
		classDependenciesResolver.resolveInternalDependencies();
		assertEquals(4, classDependenciesResolver.getClasses().size());
		assertEquals(
				List.of(
						"Modelica.Blocks.Sources.Ramp", "Modelica.Blocks.Sources.Sine", "Modelica.Units.SI.Height",
						"Modelica.Units.SI.Temperature"),
				classDependenciesResolver.getClasses().stream().sorted().toList());
	}

	@Test
	void resolveInternalDependencies_ComplexExample_resolveClassDefinitionsAbsolutePaths() throws IOException {
		String classText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
		ClassDependenciesResolver classDependenciesResolver = new ClassDependenciesResolver(
				"ComplexExample", classText);
		classDependenciesResolver.resolveInternalDependencies();
		assertEquals(7, classDependenciesResolver.getClasses().size());
		assertEquals(
				List.of(
						"Modelica.Blocks.Sources.Ramp", "Modelica.Fluid.Pipes.DynamicPipe",
						"Modelica.Fluid.Sources.Boundary_pT", "Modelica.Fluid.Sources.MassFlowSource_T",
						"Modelica.Units.SI.CrossSection", "Package.OtherPackage.Component", "Real"),
				classDependenciesResolver.getClasses().stream().sorted().toList());
	}

}