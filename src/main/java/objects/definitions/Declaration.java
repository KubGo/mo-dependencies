package objects.definitions;

import modelica.ModelicaVariability;

public class Declaration {
	String componentName;
	String className;
	String description;
	String constrainingClass;
	String value;
	ModelicaVariability variability;

	public Declaration(
			String componentName, String className, String description, String constrainingClass, String value,
			ModelicaVariability variability) {
		this.componentName = componentName;
		this.className = className;
		this.description = description;
		this.constrainingClass = constrainingClass;
		this.value = value;
		this.variability = variability;
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

	public String getComponentName() {
		return componentName;
	}

	public String getClassName() {
		return className;
	}

	public String getDescription() {
		return description;
	}

	public String getConstrainingClass() {
		return constrainingClass;
	}

	public String getValue() {
		return value;
	}
}
