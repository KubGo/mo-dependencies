package dependencies.classesinfo;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DependenciesRecordTest {

    @Test
    void toString_trivialExample_correctTextAsJson() {
        DependenciesRecord dependenciesRecord = new DependenciesRecord(
                "ModelicaClass",
                List.of("Modelica.Units.SI.Length", "Modelica.Sources.Ramp", "Library.Class")
        );
        assertEquals(
                "{modelicaPath: \"ModelicaClass\"," +
                        " dependencies: [\"Modelica.Units.SI.Length\", \"Modelica.Sources.Ramp\", \"Library.Class\"]}",
                dependenciesRecord.toString()
        );
    }
}