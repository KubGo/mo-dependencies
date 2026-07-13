package objects.modelica;

import objects.files.ModelicaFile;
import objects.files.ModelicaFolder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaFileTest {

	private final ModelicaFile modelicaClass = new ModelicaFile("Height");
	private final ModelicaFolder modelicaFolder = new ModelicaFolder("Units");
	private final ModelicaFile modelicaClassInPackage = new ModelicaFile("Height", modelicaFolder);

	@Test
	void getName_ModelicaClass_nameMatches() {
		assertEquals("Height", modelicaClass.getName());
		assertEquals("Height", modelicaClassInPackage.getName());
	}

	@Test
	void getPath_ModelicaClass_pathMatches() {
		assertEquals("Height", modelicaClass.getPath());
		assertEquals("Units.Height", modelicaClassInPackage.getPath());
	}

	@Test
	void pathMatches_modelicaClass_returnsTrue() {
		assertTrue(modelicaClass.pathMatches("Height"));
		assertTrue(modelicaClassInPackage.pathMatches("Height"));
	}

	@Test
	void getParent_modelicaClass_correctParentReturned() {
		var parent = modelicaClassInPackage.getParent();
		assertEquals("Units", parent.getName());
		assertNull(modelicaClass.getParent());
	}

	@Test
	void hasParent_modelicaClass_correctBooleanOutput() {
		assertFalse(modelicaClass.hasParent());
		assertTrue(modelicaClassInPackage.hasParent());
	}

	@Test
	void iterator_modelicaClass_worksCorrectly() {
		assertTrue(modelicaClass.hasNext());
		assertEquals("Height", modelicaClass.getNext().getName());
		assertFalse(modelicaClass.hasNext());
		modelicaClass.reset();
		assertTrue(modelicaClass.hasNext());
	}

	@Test
	void getFilePath_modelicaClass_worksCorrectly() {
		assertEquals("C:/folder/library/Height.mo",
				modelicaClass.getFilePath("C:/folder/library"));
		assertEquals("C:/folder/library/Units/Height.mo",
				modelicaClassInPackage.getFilePath("C:/folder/library"));
	}
}