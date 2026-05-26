package dependencies;

import config.Config;
import dependencies.exceptions.NoFilesToCreateDependencies;
import dependencies.structureinfo.ClassInfo;
import dependencies.structureinfo.ModelicaFileInfo;
import dependencies.structureinfo.PackageInfo;
import filtering.ModelicaDirectoryFilter;

import java.io.File;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Resolves file structure of Modelica library
 */
public class ModelicaFilesStructure {
	private PackageInfo currentPackage;
	private final Stack<PackageInfo> packagesStack = new Stack<>();
	TreeMap<String, PackageInfo> tree = new TreeMap<>();
	private String libraryName;
	private final ModelicaDirectoryFilter modelicaDirectoryFilter = new ModelicaDirectoryFilter();
	public ModelicaFilesStructure(){

	}

	/**
	 * @return Modelica files tree
	 */
	public TreeMap<String, PackageInfo> getTree() {
		return tree;
	}
	public ModelicaFilesStructure(String path, String libraryName){
		resolveFileStructure(path, libraryName);
	}

	/**
	 * @param path        path to the library top package
	 * @param libraryName name of the library
	 */
	public void resolveFileStructure(String path, String libraryName) throws NoFilesToCreateDependencies {
		if (Config.DEBUG) System.out.println("Resolving file structure for " + libraryName + "...");
		this.libraryName = libraryName;
		currentPackage = new PackageInfo(path, libraryName);
		packagesStack.push(currentPackage);
		tree.put(libraryName, currentPackage);
		try {
			getModelicaPaths(path, libraryName);
		} catch (NoFilesToCreateDependencies e) {
			throw new RuntimeException(e);
		}
		if (Config.DEBUG) System.out.println("File structure resolved.");
	}

	/**
	 * Iterates over files tree and create tree map from classes extending
	 * {@link ModelicaFileInfo}. Starts from top level package and goes down into
	 * file tree depth first. This method iteratively calls itself when reaches another
	 * Modelica package.
	 * @param path path to file
	 * @param packageName name of the package containing the file
	 */
	private void getModelicaPaths(String path, String packageName) throws NoFilesToCreateDependencies {
		if (Config.VERBOSE) System.out.println("Resolving file structure for " + path + "...");
		File[] files = new File(path).listFiles();
		if (files == null){
			throw new NoFilesToCreateDependencies(path);
		}
		for (File file: files){
			if (file.isDirectory()){
				if (modelicaDirectoryFilter.filterName(file.getPath())) {
					PackageInfo packageInfo = new PackageInfo(file.getPath(), currentPackage);
					updateCurrentPackage(packageInfo);
					String packagePath = getSubpackagePath(packageName, file.getName());
					tree.get(packageName).addChild(packageInfo);
					tree.put(packagePath, packageInfo);
					getModelicaPaths(file.getPath(), packagePath);
				}
			}
			else if (file.getName().endsWith(".mo") && !file.getName().equals("package.mo")){
				ClassInfo classInfo = new ClassInfo(file.getPath(), currentPackage);
				tree.get(packageName).addChild(classInfo);
			}
		}
		returnToPreviousPackage();
		if (Config.VERBOSE) System.out.println("Resolved file structure for " + path + ".");

	}

	private void returnToPreviousPackage(){
		currentPackage = packagesStack.pop();
	}
	private void updateCurrentPackage(PackageInfo packageInfo){
		packagesStack.push(currentPackage);
		currentPackage = packageInfo;
	}

	private String getSubpackagePath(String packageName, String fileName){
		return packageName + "." + fileName;
	}

	@Override
	public String toString() {
		int indent = 1;
		int limit = 15;
		StringBuilder builder = new StringBuilder();
		builder.append(libraryName).append("\n");
		for (String key : tree.descendingKeySet().reversed()){
			var fileInfoList = tree.get(key);
			for (ModelicaFileInfo fileInfo : fileInfoList.getChildren()){
				builder.append(fileInfo.toStringWithIndent(indent, limit));
			}
			indent++;
			if (indent > limit){
				break;
			}
		}
		return builder.toString();
	}
}
