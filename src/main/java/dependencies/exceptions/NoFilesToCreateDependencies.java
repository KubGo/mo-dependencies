package dependencies.exceptions;

public class NoFilesToCreateDependencies extends RuntimeException {
	public NoFilesToCreateDependencies(String path) {
		super("Couldn't find any files to create dependencies at " + path + ".\nMake sure the path is correct and if it is, try using an absolute path.");
	}
}
