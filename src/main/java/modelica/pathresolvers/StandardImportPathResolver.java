package modelica.pathresolvers;

import java.util.Arrays;

public class StandardImportPathResolver implements IPathResolver{
	@Override
	public boolean isSubPath(String path, String pathToTest) {
		return Arrays.stream(path.split("\\."))
				.toList()
				.getLast()
				.equals(Arrays.stream(pathToTest.split("\\."))
								.toList().getFirst());
	}
}
