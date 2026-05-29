package functionalities.classusage;

import dependencies.classesinfo.ClassDependencies;
import dependencies.readdependencies.JsonDependenciesReader;
import org.junit.jupiter.api.BeforeAll;
import utils.Utils;

import java.io.FileNotFoundException;
import java.util.Map;

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


//    @Test
//    void getTopLevelClassesList_BuildingsLibrary_findAllTopLevelClassesInSingleLibrary() {
//        classUsageResolver = new ClassUsageResolver(buildingsLibraryLite);
//        classUsageResolver.search();
//        assertEquals(
//                List.of(List.of()),
//                classUsageResolver.getTopLevelClassesList()
//        );
//    }
//
//    @Test
//    void getClassUsageTreeList_BouncingBall_getAllModelsThatUseBouncingBall() {
//        classUsageResolver = new ClassUsageResolver(buildingsLibraryLite);
//        classUsageResolver.search();
//        assertEquals(
//                List.of(),
//                classUsageResolver.getClassUsageTreeList().getFirst()
//                        .get("BuildingsLite.Tests.BouncingBall")
//        );
//    }
//
//    @Test
//    void getExtendingClassesTreeList() {
//    }
}