package filtering;

import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelicaDirectoryFilterTest {
	private final ModelicaDirectoryFilter modelicaDirectoryFilter = new ModelicaDirectoryFilter();

	 @Test
	 void filterPackage_BuildingsLibrary_modelicaPackageDiscovered(){
		 String buildingsPath = Utils.getPathAsString("BuildingsLite");
		 assertTrue(modelicaDirectoryFilter.shouldBeUsed(buildingsPath));
	 }

	@Test
	void filterPackage_BuildingsLibrary_nonModelicaPackageDiscovered(){
		String resourcesPath = Utils.getPathAsString("BuildingsLite/Resources");
		assertFalse(modelicaDirectoryFilter.shouldBeUsed(resourcesPath));
	}

}