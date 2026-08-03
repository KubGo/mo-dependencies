package facade;

import objects.classes.IModelicaClass;
import objects.classes.ModelicaPackage;
import objects.modelica.Component;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.checkStringStream;

class LibraryResolutionFacadeTest {

    private LibraryResolutionFacade facade;

    @Test
    void createLibraryPackage_BuildingsLite_verifyPackageIsCreatedCorrectly() {
        facade = new LibraryResolutionFacade();
        ModelicaPackage modelicaLibrary = facade.resolveLibraries(
                Utils.getPathAsString(Utils.BuildingsLite)).getFirst();

        IModelicaClass modifications = modelicaLibrary.getByName("BuildingsLite.Modifications.DifferenceRamp");

        checkStringStream(
                String.join("\n", Stream.of(
                        "Boolean",
                        "Real",
                        "Modelica.Blocks.Sources.Constant",
                        "Modelica.Blocks.Sources.Ramp",
                        "Modelica.Blocks.Sources.RealExpression",
                        "Modelica.Blocks.Interfaces.RealOutput",
                        "BuildingsLite.Bugfixes.Difference"
                ).sorted().toList()),
                modifications.getComponents()
                        .stream()
                        .map(Component::getClassName)
        );
    }

    @Test
    void createLibraryPackageWithoutResolvedAbsolutePaths_BuildingsLite_relativePathsUsed() {
        facade = new LibraryResolutionFacade(false, false, false);
        ModelicaPackage modelicaLibrary = facade.resolveLibraries(
                Utils.getPathAsString(Utils.BuildingsLite)).getFirst();
        checkStringStream(
                String.join("\n",
                        Stream.of("BaseClasses", "Bugfixes", "Airflow", "Tests", "Controls", "HeatTransfer", "Modifications")
                                .sorted()
                                .toList()),
                modelicaLibrary.getComponents().stream().map(Component::getClassName));
    }

    @Test
    void createLibraryPackageWithoutExtendingClasses_BuildingsLite_noExtendingClassComponents() {
        facade = new LibraryResolutionFacade(true, false, false);
        ModelicaPackage modelicaLibrary = facade.resolveLibraries(
                Utils.getPathAsString(Utils.BuildingsLite)).getFirst();
        checkStringStream(
                String.join("\n",
                        Stream.of(
                                "BuildingsLite.HeatTransfer.Conduction.SingleLayer",
                                "BuildingsLite.Controls.Discrete.BooleanDelay",
                                "BuildingsLite.Airflow.Multizone.MediumColumn",
                                "BuildingsLite.Tests.BouncingBall",
                                "OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction"
                        ).sorted().toList()),
                modelicaLibrary.getByName("BuildingsLite.Tests.SimpleModel")
                        .getComponents()
                        .stream()
                        .map(Component::getClassName)
        );
    }

    @Test
    void wrongPath_PathDoesntExist_throwError() {
        facade = new LibraryResolutionFacade();
        assertThrows(
                RuntimeException.class,
                () -> facade.resolveLibraries("SomeNonExistingPath/path")
        );
    }

    @Test
    void wrongPath_NoModelicaFiles_throwError() {
        facade = new LibraryResolutionFacade();
        File file = new File("");
        assertThrows(
                RuntimeException.class,
                () -> facade.resolveLibraries(file.getAbsolutePath() + "/src/test/java/utils")
        );
    }

    @Test
    void resolveAllLibraries_resources_bothLibrariesResolved() {
        facade = new LibraryResolutionFacade(
                false,
                false,
                true
        );
        File file = new File("src/test/resources");
        List<ModelicaPackage> libraries =
                facade.resolveLibraries(file.getAbsolutePath());
        assertEquals(2, libraries.size());
    }

    @Test
    void resolveAllLibraries_resources_extendsFromSecondLibraryResolved() {
        facade = new LibraryResolutionFacade(
                true,
                true,
                true
        );
        File file = new File("src/test/resources");
        List<ModelicaPackage> libraries =
                facade.resolveLibraries(file.getAbsolutePath());

        ModelicaPackage testLibrary = libraries.stream()
                .filter(it -> it.getClassName()
                        .equals("TestLibrary")).findFirst().orElse(null);
        assertNotNull(testLibrary);
        IModelicaClass exteriorConvection =
                testLibrary.getByName("TestLibrary.HeatTransfer.ExteriorConvectionTest");
        assertFalse(
                exteriorConvection.getComponents().isEmpty()
        );
        checkStringStream(
                String.join("\n",
                        Stream.of(
                                "Modelica.Blocks.Sources.Constant",
                                "BuildingsLite.HeatTransfer.Sources.FixedTemperature",
                                "BuildingsLite.HeatTransfer.Convection.Exterior",
                                "BuildingsLite.HeatTransfer.Sources.PrescribedTemperature",
                                "Modelica.Blocks.Sources.Ramp"
                        ).sorted().toList()),
                exteriorConvection.getComponents()
                        .stream()
                        .map(Component::getClassName)
        );
    }
}