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
				tree.tree.get("Buildings").getChildren().size());
		assertEquals(
				1,
				tree.tree.get("Buildings.Air").getChildren().size());
		assertEquals(
				10,
				tree.tree.get("Buildings.HeatTransfer.Examples")
						.getChildren()
						.size());
		assertEquals(
				7,
				tree.tree.get("Buildings.HeatTransfer.Radiosity")
						.getChildren()
						.size()
					);
	}
}