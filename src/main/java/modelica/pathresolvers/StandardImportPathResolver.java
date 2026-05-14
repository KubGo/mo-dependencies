package modelica.pathresolvers;

import modelica.PathJoiner;

import java.util.Arrays;

/**
 * Resolves absolute paths from imports
 */
public class StandardImportPathResolver implements IPathResolver{
	private String importPath;


	/**
	 * @param importPath - imported path in Modelica to check
	 */
	public void setImportPath(String importPath) {
		this.importPath = importPath;
	}

	@Override
	public boolean isSubPath(String pathToTest) {
		return Arrays.stream(importPath.split("\\."))
				.toList()
				.getLast()
				.equals(Arrays.stream(pathToTest.split("\\."))
								.toList().getFirst());
	}

	@Override
	public String getAbsolutePath(String path) {
		return PathJoiner.joinPaths(importPath, path);
	}
}
