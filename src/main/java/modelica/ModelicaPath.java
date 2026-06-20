package modelica;

import java.util.Arrays;
import java.util.List;

/**
 * Joins parser.Modelica paths together
 */
public class ModelicaPath {

	/**
	 * @param rootPath  absolute path to the package
	 * @param childPath path of the child class from the package to join
	 *
	 * @return joined path from the root through the package and to the child path,
	 * e.g.:
	 * //	 * rootPath = Library.Package.Subpackage.OtherPackage
	 * childPath = Subpackage.ChildModel
	 * returns Library.Package.Subpackage.ChildModel
	 *
	 * @throws PathsNotJoinableException when the paths cannot be joint together
	 */
	public static String joinSubPaths(String rootPath, String childPath) throws PathsNotJoinableException {
		String splitCharacter = "\\.";
		String[] rootPathSplit = rootPath.split(splitCharacter);
		String[] childPathSplit = childPath.split(splitCharacter);
		int rootPathIndex = 0;

		while (rootPathIndex < rootPathSplit.length){
			if (rootPathSplit[rootPathIndex].equals(childPathSplit[0])){
				break;
			}
			rootPathIndex++;
		}
		if (rootPathIndex == rootPathSplit.length){
			throw new PathsNotJoinableException(rootPath, childPath);
		}

		return String.join(".", Arrays.stream(rootPathSplit).limit(rootPathIndex).toList()) + "." + childPath;
	}

	public static String joinPaths(String rootPath, String childPath) {
		return rootPath + "." + childPath;
	}

	public static List<String> modelicaPathToSubPaths(String path) {
		List<String> pathParts;
		if (path.split("\\.").length < 2) {
			pathParts = List.of(path);
		}
		else {
			pathParts = Arrays.stream(path.split("\\.")).toList();
		}
		return pathParts;
	}
}
