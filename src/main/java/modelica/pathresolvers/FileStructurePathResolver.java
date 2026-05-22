package modelica.pathresolvers;

import dependencies.structureinfo.ModelicaFileInfo;
import dependencies.structureinfo.PackageInfo;

import java.util.*;

/**
 * Resolves paths from file structure tree (relative Modelica paths)
 */
public class FileStructurePathResolver implements IPathResolver {
	private final TreeMap<String, PackageInfo> filesTree;
	private List<String> pathsToResolve = List.of();
	private int pathIndex = 0;
	private PackageInfo currentPackage = null;
	private PackageInfo packageToSearch = null;
	private final Set<String> visitedPaths = new HashSet<>();


	public FileStructurePathResolver(TreeMap<String, PackageInfo> filesTree) {
		this.filesTree = filesTree;
	}

	public PackageInfo getPackageInfoByKey(String key) {
		return filesTree.get(key);
	}

	@Override
	public boolean isSubPath(String pathToTest) {
		return pathsToResolve.get(pathIndex).equals(pathToTest);
	}

	/**
	 * @param pathToResolve - Path to look for absolute path in file tree
	 *
	 * @return - absolute path or current used path if absolute one wasn't found.
	 */
	public String getAbsolutePath(String pathToResolve) {
		reset();
		setPathsToResolve(pathToResolve);
		while (continueSearch()) {
			ModelicaFileInfo file = getFile(pathsToResolve.get(pathIndex));
			if (!checkIfThePathMatches(file)) {
				continue;
			}
			if (isThisPathFinal(file)) {
				return file != null ? file.getModelicaPath() : pathToResolve;
			}
			if (file instanceof PackageInfo) {
				currentPackage = (PackageInfo) file;
			}
			else {
				currentPackage = (PackageInfo) (file != null ? file.getParent() : null);
			}
			nextClass();
		}
		return pathToResolve;
	}

	/**
	 * @param path - current class name to search in current package
	 *
	 * @return If name was found in package return its ModelicaFileInfo,
	 * else return null.
	 */
	private ModelicaFileInfo getFile(String path) {
		ModelicaFileInfo file = currentPackage.matchPath(path);
		if (file == null) {
			return null;
		}
		/* Continue if path was already searched and doesn't match,
		 to prevent infinite loop */
		if (visitedPaths.contains(file.getModelicaPath())) {
			return null;
		}
		visitedPaths.add(file.getModelicaPath());
		return file;
	}

	private void nextClass() {
		pathIndex++;
	}

	private boolean isThisPathFinal(ModelicaFileInfo file) {
		if (pathIndex < pathsToResolve.size() - 1) {
			return false;
		}
		return file.isFinal();
	}

	/**
	 * @param file - Modelica file found in package or null if file wasn't found.
	 *             If null, search in parent package.
	 *
	 * @return - true if file was found, false otherwise
	 */
	private boolean checkIfThePathMatches(ModelicaFileInfo file) {
		if (file == null) {
			if (pathIndex != 0) {
				pathIndex--;
			}
			currentPackage = currentPackage.getParent();
			return false;
		}
		return true;
	}

	/**
	 * @return Check if top all packages up to top package were checked
	 */
	private boolean continueSearch() {
		return currentPackage != null && pathIndex < pathsToResolve.size();
	}

	/**
	 * @param key - Current package name that contains class definition
	 */
	public void setCurrentPackage(String key) {
		PackageInfo packageInfo = getPackageInfoByKey(key);
		currentPackage = packageInfo;
		packageToSearch = packageInfo;
	}

	/**
	 * Rest paths resolver to check for new absolute path from file structure
	 */
	private void reset() {
		currentPackage = packageToSearch;
		pathIndex = 0;
		visitedPaths.clear();
	}


	/**
	 * Splits modelica path to packages and model to resolve in file structure
	 * @param pathsToResolve - Modelica path used in declaration
	 */
	public void setPathsToResolve(String pathsToResolve) {
		this.pathsToResolve = Arrays.stream(pathsToResolve.split("\\.")).toList();
	}
}
