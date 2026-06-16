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
				.setDependencies(
				        List.of("parser.Modelica.Units.SI.Length", "parser.Modelica.Sources.Ramp", "Library.Class"))
				.setParentClasses(List.of("parser.Modelica.Icons.Example"))
				.setConstrainingClasses(Map.of("parser.Modelica.Sources.Interfaces.SISO", "ChoicesAllMatching"))
                .build();
    }

    @Test
    void toString_trivialExample_correctTextAsJson() {
        assertEquals(
				"ClassDependencies{modelicaPath='ModelicaClass', " + "resolvedLibraries=[], " + "usedClasses=[parser.Modelica.Units.SI.Length, parser.Modelica.Sources.Ramp, Library.Class], " + "parentClasses=[parser.Modelica.Icons.Example], " + "constrainingClasses={parser.Modelica.Sources.Interfaces.SISO=ChoicesAllMatching}}",
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