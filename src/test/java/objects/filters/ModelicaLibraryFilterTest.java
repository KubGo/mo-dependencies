package objects.filters;

import objects.modelica.Component;
import objects.modelica.ComponentBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelicaLibraryFilterTest {

    ModelicaLibraryFilter filter = new ModelicaLibraryFilter("Modelica");
    ComponentBuilder builder = new ComponentBuilder();

    @Test
    void shouldBeUsed_ModelicaFilter_returnTrue() {
        Component component = builder.setClassName("Testing.class").build();
        assertTrue(
                filter.shouldBeUsed(component)
        );
        component = builder.setClassName("BuildingsLite.Class").build();
        assertTrue(
                filter.shouldBeUsed(component)
        );
    }

    @Test
    void shouldBeUsed_ModelicaFilter_returnFalse() {
        Component component = builder.setClassName("Modelica").build();
        assertFalse(
                filter.shouldBeUsed(component)
        );
        component = builder.setClassName("Modelica.Units.SI").build();
        assertFalse(
                filter.shouldBeUsed(component)
        );

    }
}