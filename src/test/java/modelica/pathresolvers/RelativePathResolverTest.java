package modelica.pathresolvers;

import modelica.packagestructure.PackageStructureResolver;
import objects.files.ModelicaFolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RelativePathResolverTest {

    static ModelicaFolder modelicaFolder;
    static RelativePathResolver relativePathResolver = new RelativePathResolver();

    @BeforeAll
    static void setUp() {
        PackageStructureResolver packageStructureResolver = new PackageStructureResolver(
                Utils.getPathAsString(Utils.BuildingsLite),
                "BuildingsLite"
        );
        modelicaFolder = packageStructureResolver.getLibraryPackage();
        relativePathResolver.setModelicaFolder(modelicaFolder.getByName("BuildingsLite.Tests.SimpleModel").getParent());
    }

    @Test
    void resolveRelativePath_TheSamePackage_resolvedPathsCorrectly() {
        assertEquals(
                "BuildingsLite.Tests.BouncingBall",
                relativePathResolver.resolvePath("BouncingBall")
        );
    }

    @Test
    void resolveRelativePath_subpackage_resolvedPathsCorrectly() {
        assertEquals(
                "BuildingsLite.Tests.HeatTransfer.Conduction.DummyModel",
                relativePathResolver.resolvePath("HeatTransfer.Conduction.DummyModel")
        );
    }

    @Test
    void resolveRelativePath_similarSubpackageButOnTopLevelPackage_resolvedPathsCorrectly() {
        assertEquals(
                "BuildingsLite.HeatTransfer.Conduction.MultiLayer",
                relativePathResolver.resolvePath("HeatTransfer.Conduction.MultiLayer")
        );
    }

    @Test
    void resolveRelativePath_otherLibraryPath_returnsTheSamePath() {
        assertEquals(
                "OtherLibrary.Path.To.Model",
                relativePathResolver.resolvePath("OtherLibrary.Path.To.Model")
        );
    }
}