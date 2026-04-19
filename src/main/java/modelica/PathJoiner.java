package modelica;

import java.util.Arrays;

public class PathJoiner {

	public static String joinPaths(String rootPath, String childPath) throws PathsNotJoinableException{
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
}
