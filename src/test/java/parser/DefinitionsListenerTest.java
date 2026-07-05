package parser;

import modelica.ComponentPrefix;
import modelica.ModelicaClassType;
import modelica.ModelicaVariability;
import objects.definitions.Component;
import objects.definitions.Modification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utils.checkStringStream;

class DefinitionsListenerTest {

    DefinitionsListener listener;

    @BeforeEach
    void setUp() {
        listener = new DefinitionsListener();
    }

    @Test
    void extractDefinitions_BoundingBall_definitionsMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.BouncingBall);
        DefinitionsListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(4, parsedListener.getComponents().size());
        checkStringStream("Height, Real, Velocity", parsedListener.getComponents()
                .stream()
                .map(Component::getClassName));

        checkStringStream("e, h, h0, v", parsedListener.getComponents()
                .stream()
                .map(Component::getComponentName));

        checkStringStream("0.8, 1.0", parsedListener.getComponents()
                .stream()
                .map(Component::getValue)
                .filter(Objects::nonNull));

        assertEquals(ModelicaVariability.PARAMETER,
                parsedListener.getComponents().getFirst().getVariability());

    }

    @Test
    void extractDefinitions_ImportsTest_definitionsMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ImportsTest);
        DefinitionsListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);
        assertEquals(2, parsedListener.getImportedClasses().size());

        assertEquals(List.of("Modelica.Units.SI", "Modelica.Blocks.Sources"), parsedListener.getImportedClasses());

        checkStringStream("T, h, ramp, sine", parsedListener.getComponents()
                .stream()
                .map(Component::getComponentName));
        checkStringStream("ramp.duration, sine.amplitude, sine.f", parsedListener.getModifications()
                .stream()
                .map(Modification::getComponent));
        assertEquals(3,
                parsedListener.getModifications().size());

    }

    @Test
    void extractDefinitions_ComplexExample_definitionsMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.ComplexExample);
        DefinitionsListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        assertEquals(
                List.of("Modelica.Icons.ExamplesPackage"),
                parsedListener.getExtendingClasses());

        checkStringStream("component, crossArea, m_flow, pipe, sink, source, temperature_A_F",
                parsedListener.getComponents()
                        .stream().map(Component::getComponentName));

        checkStringStream(String.join(", ",
                        List.of(
                                "Modelica.Blocks.Sources.Ramp",
                                "Modelica.Fluid.Sources.Boundary_pT",
                                "Modelica.Fluid.Sources.MassFlowSource_T",
                                "Package.OtherPackage.Component",
                                "Pipe",
                                "Real",
                                "SI.CrossSection")),
                parsedListener.getComponents().stream().map(Component::getClassName));

        checkStringStream("",
                parsedListener.getComponents().stream().map(Component::getValue));

        assertEquals(ModelicaVariability.VARIABLE,
                parsedListener.getComponents().getFirst().getVariability()
        );

        assertEquals("Pipe",
                parsedListener.getDeclarations().getFirst().getDeclarationName());

        assertEquals("Modelica.Fluid.Pipes.DynamicPipe",
                parsedListener.getDeclarations().getFirst().getDeclarationClass());

        assertEquals(ModelicaClassType.MODEL,
                parsedListener.getDeclarations().getFirst().getType());

        assertEquals(List.of("Modelica.Units.SI"),
                parsedListener.getImportedClasses());

        Component m_flow =
                parsedListener.getComponents()
                        .stream()
                        .filter(it -> it.getComponentName().equals("m_flow"))
                        .limit(1)
                        .toList()
                        .getFirst();

        assertEquals(ComponentPrefix.REPLACEABLE,
                m_flow.getComponentPrefix());

        assertEquals(
                "Modelica.Blocks.Interfaces.SO",
                m_flow.getConstrainingClass());
    }
}
