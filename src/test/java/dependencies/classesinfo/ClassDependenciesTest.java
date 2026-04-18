package dependencies.classesinfo;

import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;

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

}