package objects.filters;

import objects.modelica.Component;
import objects.modelica.ComponentBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseTypesFilterTest {

    ComponentBuilder builder = new ComponentBuilder();
    BaseTypesFilter filter = new BaseTypesFilter();

    @Test
    void shouldBeUsed_BaseTypesFilter_returnTrue() {
        Component component = builder.setClassName("Modelica").build();
        assertTrue(
                filter.shouldBeUsed(component)
        );
        component = builder.setClassName("BuildingsLite.RealInput").build();
        assertTrue(
                filter.shouldBeUsed(component)
        );
    }

    @Test
    void shouldBeUsed_BaseTypesFilter_returnFalse() {
        Component component = builder.setClassName("Real").build();
        assertFalse(
                filter.shouldBeUsed(component)
        );
        component = builder.setClassName("Integer").build();
        assertFalse(
                filter.shouldBeUsed(component)
        );
        component = builder.setClassName("Boolean").build();
        assertFalse(
                filter.shouldBeUsed(component)
        );
        component = builder.setClassName("String").build();
        assertFalse(
                filter.shouldBeUsed(component)
        );
    }
}