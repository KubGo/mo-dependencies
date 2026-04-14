package parser;

import modelica.ClassType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ClassesListenerTest {

    ClassesListener listener;

    @BeforeEach
    void setUp(){
        listener = new ClassesListener();
    }

    // BouncingBall.mo tests

    @Test
    void extractClasses_BouncingBall_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.BouncingBall);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                3,
                parsedListener.classes.size());
        assertEquals(
                List.of("Height", "Real", "Velocity"),
                parsedListener.classes.stream().sorted().toList());
    }

    @Test
    void extractImports_BouncingBall_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.BouncingBall);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                0,
                parsedListener.imports.size());
    }

    // ImportsTest.mo tests
    @Test
    void extractImports_ImportsTest_importsMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ImportsTest);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                2,
                parsedListener.imports.size());
    }
    @Test
    void extractClasses_ImportsTest_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ImportsTest);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                4,
                parsedListener.classes.size());
        assertEquals(
                List.of(
                        "SI.Height",
                        "SI.Temperature",
                        "Sources.Ramp",
                        "Sources.Sine"),
                parsedListener.classes.stream().sorted().toList());
    }

    // ComplexExample.mo tests

    @Test
    void extractImports_ComplexExample_importsMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                1,
                parsedListener.imports.size());
    }

    @Test
    void extractClasses_ComplexExample_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);
        assertEquals(
                7,
                parsedListener.classes.size());
        assertEquals(
                List.of(
                        "Modelica.Blocks.Sources.Ramp",
                        "Modelica.Fluid.Sources.Boundary_pT",
                        "Modelica.Fluid.Sources.MassFlowSource_T",
                        "Package.OtherPackage.Component",
                        "Pipe", // "Modelica.Fluid.Pipes.DynamicPipe",
                        "Real",
                        "SI.CrossSection"),
                parsedListener.classes.stream().sorted().toList());
    }

    @Test
    void extractExtendingClasses_ComplexExample_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                1,
                parsedListener.extendingClasses.size()
        );
        assertEquals(
            List.of("Modelica.Icons.ExamplesPackage"),
                parsedListener.extendingClasses.stream().sorted().toList()
        );
    }

    @Test
    void extractFunctionCalls_ComplexExample_classesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                1,
                parsedListener.functions.size()
        );
        assertEquals(
                List.of("Modelica.Units.Conversions.to_degF"),
                parsedListener.functions.stream().toList()
        );
    }

    @Test
    void getPackageName_ComplexExample_constraintsMapCorrect() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                "Library.Package.CustomPackage",
                parsedListener.packageName
        );
    }

    @Test
    void extractConstrainingClasses_ComplexExample_constraintsMapCorrect() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                1,
                parsedListener.constrainingClassesMap.size()
        );
        assertEquals(
                Map.of(
                        "Modelica.Blocks.Interfaces.SO",
                        "Modelica.Blocks.Sources.Ramp"
                ),
                parsedListener.constrainingClassesMap
        );
    }

    @Test
    void getExtends_ComplexExample_extendClassesMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                List.of("Modelica.Icons.ExamplesPackage"),
                parsedListener.extendingClasses
        );
    }

    @Test
    void getClassDefinitions_ComplexExample_classDefinitionMapMatches() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                Map.of(
                        "Pipe",
                        "Modelica.Fluid.Pipes.DynamicPipe"
                ),
                parsedListener.classDefinitionsMap
        );
    }

    @Test
    void getType_ComplexExample_classTypeMatches() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        ClassesListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                ClassType.MODEL,
                parsedListener.classType);
    }

}