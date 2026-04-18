package dependencies;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaFilesStructureTest {
	@Test
	void getFilesTreeStructure_BuildingsLibrary_numberOfClassesInPackagesMatch(){
		String buildingLibraryPath = System.getenv("BuildingsLibraryPath");
		ModelicaFilesStructure tree = new ModelicaFilesStructure(
				buildingLibraryPath,
				"Buildings"
		);
		assertEquals(
				19,
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
				"HeatTransfer",
				tree.tree.get("Buildings.HeatTransfer.Examples")
						.getParent()
						.getClassName());
		assertEquals(
				"Tutorial",
				tree.tree.get("Buildings.Examples.Tutorial.SpaceCooling")
						.getParent()
						.getClassName());
		assertEquals(
				7,
				tree.tree.get("Buildings.HeatTransfer.Radiosity")
						.getChildren()
						.size()
					);
	}

	@Test
	void filterNonModelicaPackages_BuildingLibrary_nonModelicaDirectoryNotIncluded(){
		String buildingLibraryPath = System.getenv("BuildingsLibraryPath");
		ModelicaFilesStructure tree = new ModelicaFilesStructure(
				buildingLibraryPath,
				"Buildings"
		);
		assertNull(tree.tree.get("Buildings.Resources"));
	}
}