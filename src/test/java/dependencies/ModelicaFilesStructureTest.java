package dependencies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaFilesStructureTest {
	@Test
	void getFilesTreeStructure_BuildingsLibrary_numberOfClassesInPackagesMatch(){
		String buildingLibraryPath = System.getenv("BuildingsLibraryPath");
		if (buildingLibraryPath == null){
			return;
		}
		ModelicaFilesStructure tree = new ModelicaFilesStructure(
				buildingLibraryPath,
				"Buildings"
		);
		assertEquals(
				20,
				tree.tree.get("Buildings").size());
		assertEquals(
				1,
				tree.tree.get("Buildings.Air").size());
		assertEquals(
				10,
				tree.tree.get("Buildings.HeatTransfer.Examples").size());
	}
}