package dependencies;

import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaFilesStructureTest {
	@Test
	void getFilesTreeStructure_BuildingsLibrary_numberOfClassesInPackagesMatch(){
		String buildingLibraryPath = Utils.getPathAsString("BuildingsLite");
		ModelicaFilesStructure tree = new ModelicaFilesStructure(
				buildingLibraryPath,
				"BuildingsLite"
		);
		assertEquals(
				3,
				tree.tree.get("BuildingsLite").getChildren().size());
		assertEquals(
				1,
				tree.tree.get("BuildingsLite.Airflow").getChildren().size());
		assertEquals(
				10,
				tree.tree.get("BuildingsLite.HeatTransfer.Examples")
						.getChildren()
						.size());
		assertEquals(
				"HeatTransfer",
				tree.tree.get("BuildingsLite.HeatTransfer.Examples")
						.getParent()
						.getClassName());
		assertEquals(
				"HeatTransfer",
				tree.tree.get("BuildingsLite.HeatTransfer.Radiosity")
						.getParent()
						.getClassName());
		assertEquals(
				7,
				tree.tree.get("BuildingsLite.HeatTransfer.Radiosity")
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