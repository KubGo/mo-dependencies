package modelica;

public class PathsNotJoinableException extends RuntimeException {
	public PathsNotJoinableException(String rootPath, String childPath) {
		super("Paths:\n" +
					  "1. " + rootPath + "\n" +
					  "2. " + childPath + "\n" +
					  "cannot be joined."
			 );
	}
}
