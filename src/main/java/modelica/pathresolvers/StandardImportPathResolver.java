package modelica.pathresolvers;

import modelica.ModelicaPathJoiner;

import java.util.Arrays;

/**
 * Resolves absolute paths from imports
 */
public class StandardImportPathResolver implements IPathResolver{
	private String importPath;


	/**
	 * @param importPath - imported path in parser.Modelica to check
	 */
	public void setImportPath(String importPath) {
		this.importPath = importPath;
	}

	@Override
	public boolean isSubPath(String pathToTest) {
		String[] listToTest = pathToTest.split("\\.");
		String name;
		if (listToTest.length < 1) {
			name = pathToTest;
		}
		else {
			name = Arrays.stream(listToTest).toList().getFirst();
		}
		return Arrays.stream(importPath.split("\\."))
				.toList()
				.getLast().equals(name);
	}

	@Override
	public String getAbsolutePath(String path) {
		return ModelicaPathJoiner.joinSubPaths(importPath, path);
	}
}
