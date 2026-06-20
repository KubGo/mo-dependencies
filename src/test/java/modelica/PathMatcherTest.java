package modelica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathMatcherTest {

    private final String fullPath = "Modelica.Electrical.TwoPorts.Condenser";
    private final String otherPath = "Modelica.Electrical.ThreePorts.Mosfet";

    @Test
    void isSubPath_theSamePaths_returnsTrue() {
        assertTrue(PathMatcher.isSubPath(fullPath, fullPath));
    }

    @Test
    void isSubPath_otherPath_returnsFalse() {
        assertFalse(PathMatcher.isSubPath(fullPath, otherPath));
    }

    @Test
    void isSubPath_twoPorts_returnsTrue() {
        assertTrue(PathMatcher.isSubPath(fullPath, "TwoPorts.Condenser"));
    }

    @Test
    void isSubPath_electrical_returnsTrue() {
        assertTrue(PathMatcher.isSubPath(fullPath, "Electrical.TwoPorts.Condenser"));
    }

    @Test
    void isSubPath_condenser_returnsTrue() {
        assertTrue(PathMatcher.isSubPath(fullPath, "Condenser"));
    }

    @Test
    void isSubPath_longerPath_returnsFalse() {
        assertFalse(PathMatcher.isSubPath(fullPath, fullPath + ".OtherComponent"));
    }

    @Test
    void isSubPath_additionalSubPath_returnsFalse() {
        assertFalse(PathMatcher.isSubPath(fullPath, "Modelica.Electrical.TwoPorts.Interfaces.Condenser"));
    }
}