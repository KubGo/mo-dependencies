package objects.modelica;

import exceptions.ModelicaClassNotFoundException;
import objects.files.ModelicaFile;
import objects.files.ModelicaFolder;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelicaFolderTest {

    private final ModelicaFolder modelicaFolderUnits = new ModelicaFolder("Units");
    private final ModelicaFolder modelicaFolderSI = new ModelicaFolder("SI", modelicaFolderUnits);
    private final ModelicaFile modelicaClassInPackage = new ModelicaFile("Height", modelicaFolderSI);
    private final ModelicaFile testClass = new ModelicaFile("Test", modelicaFolderUnits);


    @Test
    void getName_ModelicaPackage_nameMatches() {
        assertEquals("SI", modelicaFolderSI.getName());
        assertEquals("Units", modelicaFolderUnits.getName());
    }

    @Test
    void getPath_ModelicaPackage_pathMatches() {
        assertEquals("Units.SI", modelicaFolderSI.getPath());
        assertEquals("Units", modelicaFolderUnits.getPath());
        assertEquals("Units.SI.Height", modelicaClassInPackage.getPath());
    }

    @Test
    void pathMatches_ModelicaPackage_returnsTrue() {
        assertTrue(modelicaClassInPackage.pathMatches("Height"));
        assertTrue(modelicaFolderUnits.pathMatches("Height"));
        assertTrue(modelicaFolderUnits.pathMatches("Test"));
        assertTrue(modelicaFolderUnits.pathMatches("SI"));
        assertTrue(modelicaFolderSI.pathMatches("Units.SI.Height"));
    }

    @Test
    void pathMatches_ModelicaPackage_returnsFalse() {
        assertFalse(modelicaClassInPackage.pathMatches("Capacitor"));
        assertFalse(modelicaFolderUnits.pathMatches("Capacitor"));
    }

    @Test
    void getParent_ModelicaPackage_correctParentReturned() {
        var parent = modelicaClassInPackage.getParent();
        assertEquals("SI", parent.getName());
        parent = parent.getParent();
        assertEquals("Units", parent.getName());
        assertNull(parent.getParent());
    }

    @Test
    void hasParent_ModelicaPackage_correctBooleanReturned() {
        assertFalse(modelicaFolderUnits.hasParent());
        assertTrue(modelicaFolderSI.hasParent());
        assertTrue(modelicaClassInPackage.hasParent());
    }

    @Test
    void iterator_ModelicaPackage_worksCorrectly() {
        List<String> names = List.of("Units", "SI", "Height", "Test");
        for (String name : names) {
            assertTrue(modelicaFolderUnits.hasNext());
            assertEquals(name, modelicaFolderUnits.getNext().getName());
        }
        assertFalse(modelicaFolderUnits.hasNext());
        assertFalse(modelicaFolderSI.hasNext());
        assertFalse(modelicaClassInPackage.hasNext());
        modelicaFolderUnits.reset();
        assertTrue(modelicaFolderUnits.hasNext());
        assertTrue(modelicaFolderSI.hasNext());
        assertTrue(modelicaClassInPackage.hasNext());
    }

    @Test
    void getModelicaClassByName_ModelicaPackage_returnCorrectClass() {
        var height = modelicaFolderUnits.getByName("Units.SI.Height");
        assertEquals("Units.SI.Height", height.getPath());
        assertEquals("Height", height.getName());
    }

    @Test
    void getModelicaClassByName_ModelicaPackage_throwsModelicaClassNotFoundException() throws RuntimeException {
        assertThrows(
                ModelicaClassNotFoundException.class, () -> {
                    modelicaFolderUnits.getByName("Modelica.Units.SI.Height");
                });
    }

    @Test
    void getFilePath_modelicaPackage_worksCorrectly() {
        modelicaFolderUnits.setFilePath("C:/folder/package");
        assertEquals("C:/folder/package/Units/package.mo",
                modelicaFolderUnits.getFilePath());
        modelicaFolderSI.setFilePath("C:/folder/package");
        assertEquals("C:/folder/package/Units/SI/package.mo",
                modelicaFolderSI.getFilePath());
    }

    @Test
    void getChildrenPaths_modelicaPackage_getAllPackageChildrenPaths() {
        Utils.checkStringStream(
                "Units.SI.Height",
                modelicaFolderSI.getChildrenPaths()
                        .stream().sorted()
        );
        Utils.checkStringStream(
                "Units.SI\nUnits.SI.Height\nUnits.Test",
                modelicaFolderUnits.getChildrenPaths()
                        .stream().sorted()
        );
    }

}