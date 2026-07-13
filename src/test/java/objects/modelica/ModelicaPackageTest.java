package objects.modelica;

import exceptions.ModelicaClassNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaPackageTest {

	private final ModelicaPackage modelicaPackageUnits = new ModelicaPackage("Units");
	private final ModelicaPackage modelicaPackageSI = new ModelicaPackage("SI", modelicaPackageUnits);
	private final ModelicaFile modelicaClassInPackage = new ModelicaFile("Height", modelicaPackageSI);
	private final ModelicaFile testClass = new ModelicaFile("Test", modelicaPackageUnits);


	@Test
	void getName_ModelicaPackage_nameMatches() {
		assertEquals("SI", modelicaPackageSI.getName());
		assertEquals("Units", modelicaPackageUnits.getName());
	}

	@Test
	void getPath_ModelicaPackage_pathMatches() {
		assertEquals("Units.SI", modelicaPackageSI.getPath());
		assertEquals("Units", modelicaPackageUnits.getPath());
		assertEquals("Units.SI.Height", modelicaClassInPackage.getPath());
	}

	@Test
	void pathMatches_ModelicaPackage_returnsTrue() {
		assertTrue(modelicaClassInPackage.pathMatches("Height"));
		assertTrue(modelicaPackageUnits.pathMatches("Height"));
		assertTrue(modelicaPackageUnits.pathMatches("Test"));
		assertTrue(modelicaPackageUnits.pathMatches("SI"));
	}

	@Test
	void pathMatches_ModelicaPackage_returnsFalse() {
		assertFalse(modelicaClassInPackage.pathMatches("Capacitor"));
		assertFalse(modelicaPackageUnits.pathMatches("Capacitor"));
	}

	@Test
	void getParent_ModelicaPackage_correctParentReturned() {
		var parent = modelicaClassInPackage.getParent();
		assertEquals("SI", parent.getName());
		parent = parent.getParent();
		assertEquals("Units", parent.getName());
		assertNull(parent.getParent());
	}

	@Test
	void hasParent_ModelicaPackage_correctBooleanReturned() {
		assertFalse(modelicaPackageUnits.hasParent());
		assertTrue(modelicaPackageSI.hasParent());
		assertTrue(modelicaClassInPackage.hasParent());
	}

	@Test
	void iterator_ModelicaPackage_worksCorrectly() {
		List<String> names = List.of("Units", "SI", "Height", "Test");
		for (String name : names) {
			assertTrue(modelicaPackageUnits.hasNext());
			assertEquals(name, modelicaPackageUnits.getNext().getName());
		}
		assertFalse(modelicaPackageUnits.hasNext());
		assertFalse(modelicaPackageSI.hasNext());
		assertFalse(modelicaClassInPackage.hasNext());
		modelicaPackageUnits.reset();
		assertTrue(modelicaPackageUnits.hasNext());
		assertTrue(modelicaPackageSI.hasNext());
		assertTrue(modelicaClassInPackage.hasNext());
	}

	@Test
	void getModelicaClassByName_ModelicaPackage_returnCorrectClass() {
		var height = modelicaPackageUnits.getByName("Units.SI.Height");
		assertEquals("Units.SI.Height", height.getPath());
		assertEquals("Height", height.getName());
	}

	@Test
	void getModelicaClassByName_ModelicaPackage_throwsModelicaClassNotFoundException() throws RuntimeException {
		assertThrows(
				ModelicaClassNotFoundException.class, () -> {
					modelicaPackageUnits.getByName("Modelica.Units.SI.Height");
				});
	}

	@Test
	void getFilePath_modelicaPackage_worksCorrectly() {
		assertEquals("C:/folder/package/Units/package.mo",
				modelicaPackageUnits.getFilePath("C:/folder/package"));

		assertEquals("C:/folder/package/Units/SI/package.mo",
				modelicaPackageSI.getFilePath("C:/folder/package"));
	}

}