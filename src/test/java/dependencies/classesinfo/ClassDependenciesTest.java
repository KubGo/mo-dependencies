package dependencies.classesinfo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassDependenciesTest {

    static ClassDependencies classDependencies;

    @BeforeAll
    static void setUp() {
        classDependencies = new ClassDependencies(
                "ModelicaClass", List.of("Modelica.Units.SI.Length", "Modelica.Sources.Ramp", "Library.Class"));
    }

    @Test
    void toString_trivialExample_correctTextAsJson() {
        assertEquals(
                "{modelicaPath: \"ModelicaClass\"," + " dependencies: [\"Modelica.Units.SI.Length\", \"Modelica.Sources.Ramp\", \"Library.Class\"]}",
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