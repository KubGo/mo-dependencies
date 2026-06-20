package modelica.packagestructure;


import objects.modelica.ModelicaPackage;
import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackageStructureResolverTest {

	private final String pathToLibrary = Utils.getPathAsString(Utils.BuildingsLite);

	@Test
	void testCreatingPackageStructure_BuildingsLite_structureCreatedFromPathAndLibName() {
		PackageStructureResolver resolver = new PackageStructureResolver(pathToLibrary, "BuildingsLite");
		ModelicaPackage buildingsLitePackage = resolver.getLibraryPackage();
		assertEquals("BuildingsLite", buildingsLitePackage.getName());
		var coefficient = buildingsLitePackage.getByName("BuildingsLite.Airflow.Multizone.Coefficient_m_flow");
		assertEquals("Coefficient_m_flow", coefficient.getName());
	}

	@Test
	void testCreatingPackageStructure_BuildingsLite_structureCreatedFromPath() {
		PackageStructureResolver resolver = new PackageStructureResolver(pathToLibrary);
		ModelicaPackage buildingsLitePackage = resolver.getLibraryPackage();
		assertEquals("BuildingsLite", buildingsLitePackage.getName());
		var coefficient = buildingsLitePackage.getByName("BuildingsLite.Airflow.Multizone.Coefficient_m_flow");
		assertEquals("Coefficient_m_flow", coefficient.getName());
	}

}