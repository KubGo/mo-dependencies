package modelica.classdefinitions;

import modelica.ModelicaClassType;
import modelica.packagestructure.PackageStructureResolver;
import objects.classes.ModelicaPackage;
import objects.files.ModelicaFolder;
import objects.modelica.Component;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static utils.Utils.checkStringStream;

class ClassDefinitionsResolverTest {

    static ModelicaFolder modelicaFolder;
    static ClassDefinitionsResolver classDefinitionsResolver;

    @BeforeAll
    static void setUp() {
        PackageStructureResolver packageStructureResolver = new PackageStructureResolver(
                Utils.getPathAsString(Utils.BuildingsLite),
                "BuildingsLite"
        );
        modelicaFolder = packageStructureResolver.getLibraryPackage();
        classDefinitionsResolver = new ClassDefinitionsResolver(modelicaFolder);
    }


    @Test
    void verifyLibraryPackage_classDefinitionResolver_libraryPackageInfoMatches() {
        ModelicaPackage modelicaLibrary = classDefinitionsResolver.getModelicaLibrary();
        assertEquals(
                "BuildingsLite",
                modelicaLibrary.getClassName()
        );
        assertEquals(
                ModelicaClassType.PACKAGE,
                modelicaLibrary.getClassType()
        );
        checkStringStream(
                String.join(", ",
                        Stream.of("BaseClasses", "Bugfixes", "Airflow", "Tests", "Controls", "HeatTransfer")
                                .sorted()
                                .toList()),
                modelicaLibrary.getComponents().stream().map(Component::getClassName)
        );
        assertEquals(List.of(),
                modelicaLibrary.getModifications());
        assertEquals(List.of(),
                modelicaLibrary.getDeclarations());
        assertNull(
                modelicaLibrary.getParentPackage()
        );
        assertNull(
                modelicaLibrary.getNext().getParentPackage()
        );
        assertEquals(
                modelicaLibrary,
                modelicaLibrary.getNext().getParentPackage());
    }

}