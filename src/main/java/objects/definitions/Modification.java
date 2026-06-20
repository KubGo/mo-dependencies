package objects.definitions;

public class Modification {
	String component;
	String value;
	String constrainingClass = null;

	public Modification(String component, String value, String constrainingClass) {
		this.component = component;
		this.value = value;
		this.constrainingClass = constrainingClass;
	}

	public boolean hasConstraint() {
		return constrainingClass != null;
	}

}
