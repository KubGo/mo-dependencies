package facade;

import objects.classes.IModelicaClass;
import objects.classes.ModelicaPackage;
import objects.modelica.Component;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.Utils.checkStringStream;

class LibraryResolutionFacadeTest {

    private LibraryResolutionFacade facade;

    @Test
    void createLibraryPackage_BuildingsLite_verifyPackageIsCreatedCorrectly() {
        facade = new LibraryResolutionFacade();
        ModelicaPackage modelicaLibrary = facade.resolveLibrary(
                Utils.getPathAsString(Utils.BuildingsLite));

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
        ModelicaPackage modelicaLibrary = facade.resolveLibrary(
                Utils.getPathAsString(Utils.BuildingsLite));
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
        ModelicaPackage modelicaLibrary = facade.resolveLibrary(
                Utils.getPathAsString(Utils.BuildingsLite));
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
                () -> facade.resolveLibrary("SomeNonExistingPath/path")
        );
    }

    @Test
    void wrongPath_NoModelicaFiles_throwError() {
        facade = new LibraryResolutionFacade();
        File file = new File("");
        assertThrows(
                RuntimeException.class,
                () -> facade.resolveLibrary(file.getAbsolutePath() + "/src/test/java/utils")
        );
    }

}