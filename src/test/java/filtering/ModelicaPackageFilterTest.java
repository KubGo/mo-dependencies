package filtering;

import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaPackageFilterTest {
	 private final ModelicaPackageFilter modelicaPackageFilter = new ModelicaPackageFilter();

	 @Test
	 void filterPackage_BuildingsLibrary_modelicaPackageDiscovered(){
		 String buildingsPath = Utils.getPathAsString("BuildingsLite");
		 assertTrue(modelicaPackageFilter.filterName(buildingsPath));
	 }

	@Test
	void filterPackage_BuildingsLibrary_nonModelicaPackageDiscovered(){
		String resourcesPath = Utils.getPathAsString("BuildingsLite/Resources");
		assertFalse(modelicaPackageFilter.filterName(resourcesPath));
	}

}