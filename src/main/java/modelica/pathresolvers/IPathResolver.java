package modelica.pathresolvers;

/**
 * Interface for resolving modelica paths
 */
public interface IPathResolver {

	boolean isSubPath(String pathToTest);

	String getAbsolutePath(String path);
}
