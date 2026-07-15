package modelica.classdefinitions;

import modelica.packagestructure.PackageStructureResolver;
import objects.files.ModelicaFolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Utils;

class ClassDefinitionsResolverTest {

    static ModelicaFolder modelicaFolder;
    private ClassDefinitionsResolver classDefinitionsResolver;

    @BeforeAll
    static void setUp() {
        PackageStructureResolver packageStructureResolver = new PackageStructureResolver(
                Utils.getPathAsString(Utils.BuildingsLite),
                "BuildingsLite"
        );
        modelicaFolder = packageStructureResolver.getLibraryPackage();
    }


    @Test
    void libraryStructureGeneration_noErrors() {
        classDefinitionsResolver = new ClassDefinitionsResolver(modelicaFolder);

    }

}