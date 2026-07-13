package objects.modelica;

import lombok.Getter;
import modelica.ComponentPrefix;
import modelica.ModelicaVariability;

@Getter
public class Component {
    String componentName;
    String className;
    String description;
    String constrainingClass;
    String value;
    ModelicaVariability variability;
    ComponentPrefix componentPrefix;


    public Component(
            String componentName, String className, String description, String constrainingClass, String value,
            ModelicaVariability variability, ComponentPrefix componentPrefix) {
        this.componentName = componentName;
        this.className = className;
        this.description = description;
        this.constrainingClass = constrainingClass;
        this.value = value;
        this.variability = variability;
        this.componentPrefix = componentPrefix;
    }

    public boolean hasConstraint() {
        return constrainingClass != null;
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean hasClassDefinition() {
        return className != null;
    }

}
