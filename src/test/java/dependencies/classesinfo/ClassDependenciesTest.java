package dependencies.classesinfo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ClassDependenciesTest {

    static ClassDependencies classDependencies;

    @BeforeAll
    static void setUp() {
        classDependencies = new ClassDependenciesBuilder()
                .setModelicaPath("ModelicaClass")
                .setDependencies(List.of("Modelica.Units.SI.Length", "Modelica.Sources.Ramp", "Library.Class"))
                .setParentClasses(List.of("Modelica.Icons.Example"))
                .setConstrainingClasses(Map.of("Modelica.Sources.Interfaces.SISO", "ChoicesAllMatching"))
                .build();
    }

    @Test
    void toString_trivialExample_correctTextAsJson() {
        assertEquals(
                "{modelicaPath: \"ModelicaClass\"," + " dependencies: [\"Modelica.Units.SI.Length\", \"Modelica.Sources.Ramp\", \"Library.Class\"], " +
                        "parentClasses: [\"Modelica.Icons.Example\"], constrainingClasses: {{\"Modelica.Sources.Interfaces.SISO\": " +
                        "\"ChoicesAllMatching\"}}}",
                classDependencies.toString());
    }

    @Test
    void isAffected_trivialExample_classIsAffected() {
        assertTrue(classDependencies.isAffected("Library.Class"));
    }

    @Test
    void isAffected_trivialExample_classIsNotAffected() {
        assertFalse(classDependencies.isAffected("Library.Class.Subclass"));
    }

    @Test
    void isAffected_trivialExample_classIsAffectedMultipleClasses() {
        assertTrue(classDependencies.isAffected("Library.Class.Subclass", "Library.Sources.Ramp", "Library.Class"));
    }

    @Test
    void isAffected_trivialExample_classIsNotAffectedMultipleClasses() {
        assertFalse(
                classDependencies.isAffected("Library.Class.Subclass", "Library.Sources.Ramp", "Library.Class.Model"));
    }

}