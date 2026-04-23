package modelica.pathresolvers;

import dependencies.structureinfo.ModelicaFileInfo;
import dependencies.structureinfo.PackageInfo;

import java.util.*;

public class FileStructurePathResolver implements IPathResolver {
	private final TreeMap<String, PackageInfo> filesTree;
	private List<String> currentPathToResolve = List.of();
	private int pathIndex = 0;
	private PackageInfo currentPackage = null;
	private final Set<String> visitedPaths = new HashSet<>();


	public FileStructurePathResolver(TreeMap<String, PackageInfo> filesTree) {
		this.filesTree = filesTree;
	}

	@Override
	public boolean isSubPath(String pathToTest) {
		return currentPathToResolve.get(pathIndex).equals(pathToTest);
	}

	/**
	 * @param pathToResolve - Path to look for absolute path in file tree
	 *
	 * @return - absolute path or current used path if absolute one wasn't found.
	 */
	public String getAbsolutePath(String pathToResolve) {
		// TODO(Refactor method)
		setCurrentPathToResolve(pathToResolve);
		while (currentPackage != null) {
			ModelicaFileInfo file = getFile(currentPathToResolve.get(pathIndex));
			if (file == null) {
				if (pathIndex == 0) {
					setCurrentPackage(currentPackage.getParent());
					continue;
				}
				else {
					pathIndex--;
					setCurrentPackage(currentPackage.getParent());
					continue;
				}
			}
			if (file.isFinal() && pathIndex == currentPathToResolve.size() - 1) {
				return file.getModelicaPath();
			}
			if (file instanceof PackageInfo) {
				currentPackage = (PackageInfo) file;
			}
			else {
				currentPackage = (PackageInfo) file.getParent();
			}
			boolean hasNext = nextClass();
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

	private boolean nextClass() {
		pathIndex++;
		return pathIndex < currentPathToResolve.size();
	}

	public void setCurrentPackage(PackageInfo packageInfo) {
		currentPackage = packageInfo;
	}


	public void setCurrentPathToResolve(String currentPathToResolve) {
		this.currentPathToResolve = Arrays.stream(currentPathToResolve.split("\\.")).toList();
	}
}
