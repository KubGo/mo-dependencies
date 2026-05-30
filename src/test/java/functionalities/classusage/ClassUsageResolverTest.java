package functionalities.classusage;

import dependencies.classesinfo.ClassDependencies;
import dependencies.readdependencies.JsonDependenciesReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassUsageResolverTest {

    static JsonDependenciesReader reader;
    static Map<String, ClassDependencies> buildingsLibraryLite;
    static Map<String, ClassDependencies> testsLibrary;
    ClassUsageResolver classUsageResolver;

    @BeforeAll
    static void setUp() {
        reader = new JsonDependenciesReader(Utils.getPathAsString(Utils.BuildingsLite));
        try {
            buildingsLibraryLite = reader.readDependencies();
            reader.setPath(Utils.getPathAsString(Utils.TestLibrary));
            testsLibrary = reader.readDependencies();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void getTopLevelClassesList_BuildingsLibrary_findAllTopLevelClassesInSingleLibrary() {
        classUsageResolver = new ClassUsageResolver(buildingsLibraryLite);
        classUsageResolver.search();
        assertEquals(474, classUsageResolver.getTopLevelClassesList().getFirst().size());
    }

    @Test
    void getClassUsageTreeList_BouncingBall_getAllModelsThatUseBouncingBall() {
        classUsageResolver = new ClassUsageResolver(buildingsLibraryLite);
        classUsageResolver.search();
        assertEquals(
                Stream.of("BuildingsLite.Tests.MultipleExtendsModel", "BuildingsLite.Tests.SimpleModel")
                        .sorted()
                        .toList(), classUsageResolver.getClassUsageTreeList()
                        .getFirst()
                        .get("BuildingsLite.Tests.BouncingBall")
                        .stream()
                        .sorted()
                        .toList());
    }

    @Test
    void getExtendingClassesTreeList_BuildingsLite_getExtendingClasses() {
        classUsageResolver = new ClassUsageResolver(buildingsLibraryLite);
        classUsageResolver.search();
        assertEquals(
                Stream.of("BuildingsLite.Tests.MultipleExtendsModel", "BuildingsLite.Tests.SimpleModel")
                        .sorted()
                        .toList(), classUsageResolver.getExtendingClassesTreeList()
                        .getFirst()
                        .get("BuildingsLite.Tests.PartialSimpleModel")
                        .stream()
                        .sorted()
                        .toList());
        assertEquals(
                Stream.of("BuildingsLite.Tests.MultipleExtendsModel").sorted().toList(),
                classUsageResolver.getExtendingClassesTreeList()
                        .getFirst()
                        .get("BuildingsLite.Tests.HeatTransfer.Conduction.DummyModel")
                        .stream()
                        .sorted()
                        .toList());
    }

    @Test
    void getClassUsage_TestLibraryAndBuildingsLite_getUsageFromTwoLibrariesOfBuildingsLite() {
        classUsageResolver = new ClassUsageResolver(buildingsLibraryLite, testsLibrary);
        classUsageResolver.search();
        assertEquals(
                Stream.of(
                        "BuildingsLite.Tests.MultipleExtendsModel", "BuildingsLite.Tests.SimpleModel",
                        "TestLibrary.Tests.BouncingBallTest").sorted().toList(),
                classUsageResolver.getClassUsageTreeList()
                        .getFirst()
                        .get("BuildingsLite.Tests.BouncingBall")
                        .stream()
                        .sorted()
                        .toList());
    }

    @Test
    void getExtendingClasses_TestLibraryAndBuildingsLite_getUsageFromTwoLibrariesOfBuildingsLite() {
        classUsageResolver = new ClassUsageResolver(testsLibrary, buildingsLibraryLite);
        classUsageResolver.search();
        assertEquals(
                Stream.of("TestLibrary.Tests.RadiocityTestCase").toList(),
                classUsageResolver.getExtendingClassesTreeList()
                        .getFirst()
                        .get("TestLibrary.Tests.PartialTestCase")
                        .stream()
                        .sorted()
                        .toList());
    }
}