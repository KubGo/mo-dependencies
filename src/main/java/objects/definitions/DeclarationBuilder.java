package objects.definitions;

public class DeclarationBuilder {
	private String componentName = "";
	private String className = null;
	private String description = null;
	private String constrainingClass = null;
	private String value = null;

	public DeclarationBuilder setComponentName(String componentName) {
		this.componentName = componentName;
		return this;
	}

	public DeclarationBuilder setClassName(String className) {
		this.className = className;
		return this;
	}

	public DeclarationBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public DeclarationBuilder setConstrainingClass(String constrainingClass) {
		this.constrainingClass = constrainingClass;
		return this;
	}

	public DeclarationBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	public Declaration createDeclaration() {
		return new Declaration(componentName, className, description, constrainingClass, value);
	}

	void reset() {
		componentName = "";
		className = null;
		description = null;
		constrainingClass = null;
		value = null;
	}

}