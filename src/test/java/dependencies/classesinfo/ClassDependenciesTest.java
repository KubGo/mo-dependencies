package dependencies.classesinfo;

import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassDependenciesTest {


	@Test
	void getDependencies_BouncingBall_correctClasses() throws IOException {
		String classText = Utils.getModelicaTextFromResources(Utils.BouncingBall);

		ClassDependencies classDependencies = new ClassDependencies("BouncingBall", classText);
		assertEquals(
				3,
				classDependencies.getAbsolutePathsClassList().size());
	}

	@Test
	void getDependencies_ImportsTest_importedClassesAbsolutePathsMatch() throws IOException {
		String classText = Utils.getModelicaTextFromResources(Utils.ImportsTest);
		ClassDependencies classDependencies = new ClassDependencies("ImportsTest", classText);
		classDependencies.resolveInternalDependencies();
		assertEquals(
				4,
				classDependencies.getAbsolutePathsClassList().size());
		assertEquals(
				List.of(
						"Modelica.Blocks.Sources.Ramp",
						"Modelica.Blocks.Sources.Sine",
						"Modelica.Units.SI.Height",
						"Modelica.Units.SI.Temperature"),
				classDependencies.getAbsolutePathsClassList().stream().sorted().toList());
	}

}