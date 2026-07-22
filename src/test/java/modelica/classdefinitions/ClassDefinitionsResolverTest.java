package modelica.classdefinitions;

import modelica.ModelicaClassType;
import modelica.packagestructure.PackageStructureResolver;
import objects.classes.IModelicaClass;
import objects.classes.ModelicaPackage;
import objects.files.ModelicaFolder;
import objects.modelica.Component;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static utils.Utils.checkStringStream;

class ClassDefinitionsResolverTest {

    static ModelicaFolder modelicaFolder;
    ClassDefinitionsResolver classDefinitionsResolver;
    ModelicaPackage modelicaLibrary;

    @BeforeAll
    static void setUp() {
        PackageStructureResolver packageStructureResolver = new PackageStructureResolver(
                Utils.getPathAsString(Utils.BuildingsLite),
                "BuildingsLite"
        );
        modelicaFolder = packageStructureResolver.getLibraryPackage();
    }

    @BeforeEach
    void setUpEach() {
        classDefinitionsResolver = new ClassDefinitionsResolver(modelicaFolder);
        modelicaLibrary = classDefinitionsResolver.getModelicaLibrary();
    }


    @Test
    void verifyLibraryPackage_classDefinitionResolver_libraryPackageInfoMatches() {
        assertEquals(
                "BuildingsLite",
                modelicaLibrary.getClassName()
        );
        assertEquals(
                ModelicaClassType.PACKAGE,
                modelicaLibrary.getClassType()
        );
        checkStringStream(
                String.join("\n",
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

    @Test
    void getClassesByName_classDefinitionsResolver_definitionsMatch() {
        String classPath = "BuildingsLite.Tests.BouncingBall";
        IModelicaClass bouncingBall = modelicaLibrary.getByName(classPath);
        assertEquals(
                classPath,
                bouncingBall.getClassPath()
        );
        assertEquals(
                "BuildingsLite.Tests",
                bouncingBall.getParentPackage().getClassPath()
        );

        checkStringStream(
                "Real",
                bouncingBall
                        .getComponents().stream()
                        .map(Component::getClassName));

    }

    @Test
    void getComponentsWithResolvedImports_ImportsTests_fullClassNamesRetrieved() {
        IModelicaClass importsTest = modelicaLibrary.getByName("BuildingsLite.Tests.ImportsTest");

        checkStringStream(
                "Modelica.Blocks.Sources.Ramp\nModelica.Blocks.Sources.Sine\nModelica.Units.SI.Height\nModelica.Units.SI.Temperature",
                importsTest.getComponents().stream()
                        .map(Component::getClassName)
        );
    }

    @Test
    void resolveDeclarations_ComplexExample_PipeDeclarationResolved() {
        IModelicaClass complexExamples = modelicaLibrary.getByName("BuildingsLite.Tests.ComplexExample");

        checkStringStream(
                String.join("\n",
                        Stream.of(
                                "Modelica.Fluid.Pipes.DynamicPipe",
                                "Modelica.Fluid.Sources.MassFlowSource_T",
                                "Modelica.Fluid.Sources.Boundary_pT",
                                "Modelica.Blocks.Sources.Ramp",
                                "Real",
                                "Modelica.Units.SI.CrossSection",
                                "Package.OtherPackage.Component"
                        ).sorted().toList()),
                complexExamples.getComponents()
                        .stream()
                        .map(Component::getClassName)
        );
    }

    @Test
    void resolveRelativePaths_SimpleModel_absolutePathsUsed() {
        modelicaLibrary.resolveRelativePaths(modelicaFolder);
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
    void resolveExtendingClasses_SimpleModel_componentsFromPartialModelIncluded() {
        modelicaLibrary.resolveExtendingClasses(modelicaLibrary);
        IModelicaClass simpleModel = modelicaLibrary.getByName("BuildingsLite.Tests.SimpleModel");
        checkStringStream(
                String.join("\n", Stream.of(
                        "BuildingsLite.HeatTransfer.Conduction.SingleLayer",
                        "BuildingsLite.Controls.Discrete.BooleanDelay",
                        "BuildingsLite.Airflow.Multizone.MediumColumn",
                        "BuildingsLite.Tests.BouncingBall",
                        "OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction",
                        "Modelica.Units.SI.Length",
                        "Modelica.Units.SI.Area",
                        "BuildingsLite.HeatTransfer.Radiosity.Constant"
                ).sorted().toList()),
                simpleModel.getComponents()
                        .stream()
                        .map(Component::getClassName)
        );

        IModelicaClass multiExtendsModel = modelicaLibrary.getByName("BuildingsLite.Tests.MultipleExtendsModel");
        checkStringStream(
                String.join("\n", Stream.of(
                        "BuildingsLite.HeatTransfer.Conduction.SingleLayer",
                        "BuildingsLite.Controls.Discrete.BooleanDelay",
                        "BuildingsLite.Airflow.Multizone.MediumColumn",
                        "BuildingsLite.Tests.BouncingBall",
                        "OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction",
                        "Modelica.Units.SI.Length",
                        "Modelica.Units.SI.Area",
                        "BuildingsLite.HeatTransfer.Radiosity.Constant",
                        "BuildingsLite.Controls.Predictors.ElectricalLoad"
                ).sorted().toList()),
                multiExtendsModel.getComponents()
                        .stream()
                        .map(Component::getClassName)
        );
    }
}