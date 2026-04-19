package modelica.pathresolvers;

public interface IPathResolver {
	boolean isSubPath(String pathToTest);

	String getAbsolutePath(String path);
}
