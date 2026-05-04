package dependencies.classesinfo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassDependenciesTest {

    @Test
    void toString_trivialExample_correctTextAsJson() {
        ClassDependencies classDependencies = new ClassDependencies(
                "ModelicaClass", List.of("Modelica.Units.SI.Length", "Modelica.Sources.Ramp", "Library.Class"));
        assertEquals(
                "{modelicaPath: \"ModelicaClass\"," + " dependencies: [\"Modelica.Units.SI.Length\", \"Modelica.Sources.Ramp\", \"Library.Class\"]}",
                classDependencies.toString());
    }
}