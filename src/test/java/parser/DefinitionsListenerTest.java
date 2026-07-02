package parser;

import objects.definitions.Declaration;
import objects.definitions.Modification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

		assertEquals(4, parsedListener.getDefinitions().size());
		assertEquals(
				"Height, Real, Velocity", String.join(
						", ", parsedListener.getDefinitions()
								.stream()
								.map(Declaration::getClassName)
								.distinct()
								.sorted()
								.toList()));

		assertEquals(
				"e, h, h0, v", String.join(
						", ", parsedListener.getDefinitions()
								.stream()
								.map(Declaration::getComponentName)
								.distinct()
								.sorted()
								.toList()));

		assertEquals(
				"0.8, 1.0", String.join(
						", ", parsedListener.getDefinitions()
								.stream()
								.map(Declaration::getValue)
								.filter(Objects::nonNull)
								.distinct()
								.sorted()
								.toList()));

	}

	@Test
	void extractDefinitions_ImportsTest_definitionsMatch() throws IOException {
		String modelicaText = Utils.getModelicaTextFromResources(Utils.ImportsTest);
		DefinitionsListener parsedListener = Utils.getParsedListenerFromText(modelicaText, listener);
		assertEquals(2, parsedListener.getImportedClasses().size());

		assertEquals(List.of("Modelica.Units.SI", "Modelica.Blocks.Sources"), parsedListener.getImportedClasses());

		assertEquals(
				"T, h, ramp, sine", String.join(
						", ", parsedListener.getDefinitions()
								.stream()
								.map(Declaration::getComponentName)
								.distinct()
								.sorted()
								.toList()));
		assertEquals("ramp.duration, sine.amplitude, sine.f",
				String.join(
						", ", parsedListener.getModifications()
								.stream()
								.map(Modification::getComponent)
								.distinct()
								.sorted()
								.toList()));
		assertEquals(3,
				parsedListener.getModifications().size());

	}

}