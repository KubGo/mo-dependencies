package exceptions;

public class ModelicaClassNotFoundException extends RuntimeException {
	public ModelicaClassNotFoundException(String name) {
		super("Class " + name + " cannot be found.");
	}
}
