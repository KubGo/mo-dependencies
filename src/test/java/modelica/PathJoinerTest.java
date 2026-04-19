package modelica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathJoinerTest {

	@Test
	void joinPaths_lastElementJoin_correctJoinedPath() {
		assertEquals(
				"Libray.Package.SubPackage.InnerPackage.Model",
				PathJoiner.joinPaths("Libray.Package.SubPackage.InnerPackage", "InnerPackage.Model"));
	}

	@Test
	void joinPaths_twoPackagesInChildPath_correctJoinedPath() {
		assertEquals(
				"Libray.Package.SubPackage.InnerPackage.Model",
				PathJoiner.joinPaths("Libray.Package.SubPackage.InnerPackage", "SubPackage.InnerPackage.Model"));
	}

	@Test
	void joinPaths_additionalPackagesInChildClass_correctJoinedPath() {
		assertEquals(
				"Libray.Package.SubPackage.InnerPackage.Model",
				PathJoiner.joinPaths("Libray.Package", "Package.SubPackage.InnerPackage.Model"));
	}

	@Test
	void joinPaths_pathsCannotBeJoined_throwsException() {
		assertThrows(
				PathsNotJoinableException.class,
				() -> PathJoiner.joinPaths("Libray.Package2", "Package.SubPackage.InnerPackage.Model"));
	}

}