package filtering;

import org.junit.jupiter.api.Test;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.*;

class ModelicaPackageFilterTest {
	 private final ModelicaPackageFilter modelicaPackageFilter = new ModelicaPackageFilter();

	 @Test
	 void filterPackage_BuildingsLibrary_modelicaPackageDiscovered(){
		 String buildingsPath = System.getenv("BuildingsLibraryPath");
		 assertTrue(modelicaPackageFilter.filterName(buildingsPath));
	 }

	@Test
	void filterPackage_BuildingsLibrary_nonModelicaPackageDiscovered(){
		String buildingsPath = System.getenv("BuildingsLibraryPath");
		String resourcesPath = Paths.get(buildingsPath, "Resources").toString();
		assertFalse(modelicaPackageFilter.filterName(resourcesPath));
	}

}