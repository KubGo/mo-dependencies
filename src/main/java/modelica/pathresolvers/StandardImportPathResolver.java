package modelica.pathresolvers;

import modelica.PathJoiner;

import java.util.Arrays;

public class StandardImportPathResolver implements IPathResolver{
	private String importPath;


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
