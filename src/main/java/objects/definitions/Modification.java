package objects.definitions;

import lombok.Getter;

public class Modification {
	@Getter
	String component;
	@Getter
	String value;
	@Getter
	String constrainingClass = null;

	public Modification(String component, String value, String constrainingClass) {
		this.component = component;
		this.value = value;
		this.constrainingClass = constrainingClass;
	}

	public Modification(String component, String value) {
		this(component, value, null);
	}

	public boolean hasConstraint() {
		return constrainingClass != null;
	}

}
