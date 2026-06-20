package modelica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelicaPathTest {

	@Test
	void joinPaths_lastElementJoin_Sub_correctJoinedPath() {
		assertEquals(
				"Libray.Package.SubPackage.InnerPackage.Model",
				ModelicaPath.joinSubPaths("Libray.Package.SubPackage.InnerPackage", "InnerPackage.Model"));
	}

	@Test
	void joinSubPaths_twoPackagesInChildPath_correctJoinedPath() {
		assertEquals(
				"Libray.Package.SubPackage.InnerPackage.Model", ModelicaPath.joinSubPaths(
						"Libray.Package.SubPackage.InnerPackage",
						"SubPackage.InnerPackage.Model"));
	}

	@Test
	void joinSubPaths_additionalPackagesInChildClass_correctJoinedPath() {
		assertEquals(
				"Libray.Package.SubPackage.InnerPackage.Model",
				ModelicaPath.joinSubPaths("Libray.Package", "Package.SubPackage.InnerPackage.Model"));
	}

	@Test
	void joinPaths_Sub_pathsCannotBeJoined_throwsException() {
		assertThrows(
				PathsNotJoinableException.class,
				() -> ModelicaPath.joinSubPaths("Libray.Package2", "Package.SubPackage.InnerPackage.Model"));
	}

}