package modelica.pathresolvers;

import dependencies.structureinfo.ModelicaFileInfo;
import dependencies.structureinfo.PackageInfo;

import java.util.*;

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
		resetCurrentPackage();
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

	private ModelicaFileInfo getFile(String path) {
		ModelicaFileInfo file = currentPackage.matchPath(path);
		if (file == null) {
			return null;
		}
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

	private boolean continueSearch() {
		return currentPackage != null;
	}

	public void setCurrentPackage(PackageInfo packageInfo) {
		currentPackage = packageInfo;
		packageToSearch = packageInfo;
	}

	private void resetCurrentPackage() {
		currentPackage = packageToSearch;
		pathIndex = 0;
		visitedPaths.clear();
	}


	public void setPathsToResolve(String pathsToResolve) {
		this.pathsToResolve = Arrays.stream(pathsToResolve.split("\\.")).toList();
	}
}
