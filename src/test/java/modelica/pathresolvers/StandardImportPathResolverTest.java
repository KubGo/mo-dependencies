package modelica.pathresolvers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardImportPathResolverTest {
	StandardImportPathResolver pathResolver = new StandardImportPathResolver();
	@Test
	void isSubPath_directImport_importedClassDiscovered() {
		pathResolver.setImportPath("Package.ImportedModel");
		assertTrue(
				pathResolver.isSubPath(
						"ImportedModel")
				  );
	}

	@Test
	void isSubPath_importPackage_importedClassDiscovered() {
		pathResolver.setImportPath("Modelica.Units.SI");
		assertTrue(
				pathResolver.isSubPath(
						"SI.Length")
		          );
	}

	@Test
	void isSubPath_differentName_importedClassNotDiscovered() {
		pathResolver.setImportPath("Modelica.Units.SI");
		assertFalse(
				pathResolver.isSubPath(
						"SIUnits.Length")
		          );
	}

	@Test
	void isSubPath_differentTopPackage_importedClassNotDiscovered() {
		pathResolver.setImportPath("Modelica.Units.SI");
		assertFalse(
				pathResolver.isSubPath("Package.SI.Length"));
	}

	@Test
	void getAbsolutePath_importedClass_AbsolutePathMatch() {
		pathResolver.setImportPath("Modelica.Units.SI");
		assertEquals("Modelica.Units.SI.Length", pathResolver.getAbsolutePath("SI.Length"));
	}
}