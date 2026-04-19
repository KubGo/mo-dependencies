package modelica.pathresolvers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardImportPathResolverTest {
	StandardImportPathResolver pathResolver = new StandardImportPathResolver();
	@Test
	void isSubPath_directImport_importedClassDiscovered() {
		assertTrue(
				pathResolver.isSubPath(
						"Package.ImportedModel",
						"ImportedModel")
				  );
	}

	@Test
	void isSubPath_importPackage_importedClassDiscovered() {
		assertTrue(
				pathResolver.isSubPath(
						"Modelica.Units.SI",
						"SI.Length")
		          );
	}

	@Test
	void isSubPath_differentName_importedClassNotDiscovered() {
		assertFalse(
				pathResolver.isSubPath(
						"Modelica.Units.SI",
						"SIUnits.Length")
		          );
	}

	@Test
	void isSubPath_differentTopPackage_importedClassNotDiscovered() {
		assertFalse(
				pathResolver.isSubPath(
						"Modelica.Units.SI",
						"Package.SI.Length")
		           );
	}

}