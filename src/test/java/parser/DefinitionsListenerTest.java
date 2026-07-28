package parser;

import modelica.ComponentPrefix;
import modelica.ModelicaClassType;
import modelica.ModelicaVariability;
import objects.modelica.Component;
import objects.modelica.Modification;
import objects.modelica.Redeclaration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
        checkStringStream("Height\nReal\nVelocity", parsedListener.getComponents()
                .stream()
                .map(Component::getClassName));

        checkStringStream("e\nh\nh0\nv", parsedListener.getComponents()
                .stream()
                .map(Component::getComponentName));

        checkStringStream("0.8\n1.0", parsedListener.getComponents()
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

        checkStringStream("T\nh\nramp\nsine", parsedListener.getComponents()
                .stream()
                .map(Component::getComponentName));
        checkStringStream("ramp.duration\nsine.amplitude\nsine.f", parsedListener.getModifications()
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

        checkStringStream("component\ncrossArea\nm_flow\npipe\nsink\nsource\ntemperature_A_F",
                parsedListener.getComponents()
                        .stream().map(Component::getComponentName));

        checkStringStream(String.join("\n",
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

        checkStringStream(
                String.join("\n", Stream.of(
                                "pipe.Medium",
                                "pipe.allowFlowReversal",
                                "pipe.length",
                                "pipe.diameter",
                                "pipe.FlowModel",
                                "pipe.FlowModel.m_flows.start",
                                "pipe.FlowModel.dps_fg.start",
                                "pipe.FlowModel.Re_turbulent",
                                "pipe.FlowModel.dp_nominal",
                                "pipe.FlowModel.m_flow_nominal",
                                "pipe.energyDynamics",
                                "pipe.massDynamics",
                                "pipe.T_start",
                                "pipe.momentumDynamics",
                                "pipe.use_HeatTransfer",
                                "pipe.HeatTransfer",
                                "pipe.HeatTransfer.k",
                                "pipe.HeatTransfer.T_ambient",
                                "pipe.HeatTransfer.alpha0",
                                "pipe.nNodes",
                                "pipe.p_b_start",
                                "pipe.m_flow_start",
                                "source.use_m_flow_in",
                                "source.nPorts",
                                "source.Medium",
                                "source.T",
                                "sink.nPorts",
                                "sink.p",
                                "sink.T",
                                "sink.Medium",
                                "m_flow.height",
                                "m_flow.duration",
                                "m_flow.offset",
                                "m_flow.startTime",
                                "component.m_flow"
                        )
                        .sorted().toList()),
                parsedListener.getModifications()
                        .stream()
                        .map(Modification::getComponent)
        );
    }

    @Test
    void extractDefinitions_SimpleModel_definitionsMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.SimpleModel);
        DefinitionsListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        checkStringStream(String.join("\n",
                        Stream.of(
                                "layer",
                                "booleanDelay",
                                "col",
                                "bouncingBall",
                                "conductionModel"
                        ).sorted().toList()),
                parsedListener.getComponents()
                        .stream().map(Component::getComponentName));

        checkStringStream(String.join("\n",
                        Stream.of(
                                "HeatTransfer.Conduction.SingleLayer",
                                "Controls.Discrete.BooleanDelay",
                                "Airflow.Multizone.MediumColumn",
                                "BouncingBall",
                                "OtherLibrary.HeatTransfer.Conduction.DiscretizedConduction"
                        ).sorted().toList()),
                parsedListener.getComponents()
                        .stream().map(Component::getClassName));

        checkStringStream(String.join("\n",
                        Stream.of(
                                "layer.A",
                                "layer.material",
                                "booleanDelay.samplePeriod",
                                "col.densitySelection"
                        ).sorted().toList()),
                parsedListener.getModifications()
                        .stream().map(Modification::getComponent));

        checkStringStream(String.join("\n",
                        Stream.of(
                                "1",
                                "BuildingsLite.HeatTransfer.Data.Solids.Concrete",
                                "BuildingsLite.Airflow.Multizone.Types.densitySelection.fromTop"
                        ).sorted().toList()),
                parsedListener.getModifications()
                        .stream().map(Modification::getValue));

        assertEquals(
                "PartialSimpleModel",
                String.join(", ", parsedListener.getExtendingClasses())
        );
    }

    @Test
    void extractRedeclaration_DifferenceRamp_redeclarationMatch() throws IOException {
        String modelicaText = Utils.getModelicaTextFromResources(Utils.DifferenceRamp);
        DefinitionsListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);

        checkStringStream("",
                parsedListener.getComponents()
                        .stream().map(Component::getClassName));

        assertEquals(0, parsedListener.getComponents().size());

        checkStringStream(
                String.join("\n", Stream.of(
                        "input1",
                        "calculation"
                ).sorted().toList()),
                parsedListener.getRedeclarations()
                        .stream().map(Redeclaration::getComponent)
        );

        checkStringStream(
                String.join("\n", Stream.of(
                        "Modelica.Blocks.Sources.Ramp",
                        "BuildingsLite.Bugfixes.Difference"
                ).sorted().toList()),
                parsedListener.getRedeclarations()
                        .stream().map(Redeclaration::getClassName)
        );

        checkStringStream(
                String.join("\n", List.of(
                        "Modelica.Blocks.Interfaces.SO"
                )),
                parsedListener.getRedeclarations()
                        .stream().map(Redeclaration::getConstrainingClass)
                        .filter(Objects::nonNull)
        );
    }
}
