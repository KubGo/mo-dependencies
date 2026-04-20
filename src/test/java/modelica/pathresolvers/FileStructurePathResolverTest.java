package modelica.pathresolvers;

import dependencies.ModelicaFilesStructure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.*;

class FileStructurePathResolverTest {
	FileStructurePathResolver fileStructurePathResolver;
	ModelicaFilesStructure modelicaFilesStructure;

	@BeforeEach
	void setUpFilesTree() {
		String buildingsLitePath = Utils.getPathAsString(Utils.BuildingsLite);
		modelicaFilesStructure = new ModelicaFilesStructure(buildingsLitePath, "BuildingsLite");
		fileStructurePathResolver = new FileStructurePathResolver(modelicaFilesStructure.getTree());
		fileStructurePathResolver.setCurrentPackage(modelicaFilesStructure.getTree().get("BuildingsLite.Tests"));
	}

	@Test
	void isSubPath_correctPath_returnTrue() {
		fileStructurePathResolver.setCurrentPathToResolve("BouncingBall");
		assertTrue(fileStructurePathResolver.isSubPath("BouncingBall"));
	}

	@Test
	void isSubPath_incorrectPath_returnFalse() {
		fileStructurePathResolver.setCurrentPathToResolve("BouncingBall");
		assertFalse(fileStructurePathResolver.isSubPath("ComplexExample"));
	}

	@Test
	void getAbsolutePath_modelInTheSamePackage_getAbsolutePathOfBouncingBallModel() {
		String absolutePath = fileStructurePathResolver.getAbsolutePath("BouncingBall");
		assertEquals("BuildingsLite.Tests.BouncingBall", absolutePath);
	}

	@Test
	void getAbsolutePath_modelInDifferentPackage_getAbsolutePathOfBooleanDelay() {
		String absolutePath = fileStructurePathResolver.getAbsolutePath("Controls.Discrete.BooleanDelay");
		assertNotNull(modelicaFilesStructure.getTree().get("BuildingsLite.Tests"));
		assertNotNull(modelicaFilesStructure.getTree().get("BuildingsLite.Tests").getParent());
		assertEquals(
				"BuildingsLite",
				modelicaFilesStructure.getTree().get("BuildingsLite.Tests").getParent().getModelicaPath());
		assertEquals("BuildingsLite.Controls.Discrete.BooleanDelay", absolutePath);
	}

	@Test
	void getAbsolutePath_modelInDifferentPackage_getAbsolutePathOfSingleLayer() {
		String absolutePath = fileStructurePathResolver.getAbsolutePath("HeatTransfer.Conduction.SingleLayer");
		assertEquals("BuildingsLite.HeatTransfer.Conduction.SingleLayer", absolutePath);
	}

	@Test
	void getAbsolutePath_modelFromDifferentLibrary_theSamePathReturned() {
		String absolutePath = fileStructurePathResolver.getAbsolutePath(
				"OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction");
		assertEquals("OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction", absolutePath);
	}
}