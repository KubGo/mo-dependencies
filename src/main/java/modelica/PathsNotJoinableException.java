package modelica;

/**
 * Exception for paths that cannot be joint
 */
public class PathsNotJoinableException extends RuntimeException {
	/**
	 * @param rootPath  - absolute path from root to some package
	 * @param childPath - child path that cannot be joint with root path
	 */
	public PathsNotJoinableException(String rootPath, String childPath) {
		super("Paths:\n" +
					  "1. " + rootPath + "\n" +
					  "2. " + childPath + "\n" +
					  "cannot be joined."
			 );
	}
}
