package objects.definitions;

import modelica.ComponentPrefix;
import modelica.ModelicaVariability;

public class ComponentBuilder implements IBuilder<Component> {
	private String componentName = null;
	private String className = null;
	private String description = null;
	private String constrainingClass = null;
	private String value = null;
	private ComponentPrefix componentPrefix = ComponentPrefix.NONE;

	private ModelicaVariability variability = ModelicaVariability.VARIABLE;

	public ComponentBuilder setComponentName(String componentName) {
		this.componentName = componentName;
		return this;
	}

	public ComponentBuilder setClassName(String className) {
		this.className = className;
		return this;
	}

	public ComponentBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public ComponentBuilder setConstrainingClass(String constrainingClass) {
		this.constrainingClass = constrainingClass;
		return this;
	}

	public ComponentBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public Component build() {
		return new Component(componentName, className, description, constrainingClass, value, variability, componentPrefix);
	}

	@Override
	public boolean isReady() {
		return className != null && componentName != null;
	}

	public void reset() {
		componentName = null;
		className = null;
		description = null;
		constrainingClass = null;
		value = null;
		variability = ModelicaVariability.VARIABLE;
		componentPrefix = ComponentPrefix.NONE;
	}

	public ComponentBuilder setVariability(ModelicaVariability variability) {
		this.variability = variability;
		return this;
	}

	public ComponentBuilder setComponentPrefix(ComponentPrefix componentPrefix) {
		this.componentPrefix = componentPrefix;
		return this;
	}
}