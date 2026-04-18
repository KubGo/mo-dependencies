package dependencies;

import dependencies.structureinfo.ClassInfo;
import dependencies.structureinfo.ModelicaFileInfo;
import dependencies.structureinfo.PackageInfo;
import filtering.ModelicaPackageFilter;

import java.io.File;
import java.util.Stack;
import java.util.TreeMap;

public class ModelicaFilesStructure {
	private PackageInfo currentPackage;
	private final Stack<PackageInfo> packagesStack = new Stack<>();
	TreeMap<String, PackageInfo> tree = new TreeMap<>();
	private String libraryName;
	private final ModelicaPackageFilter modelicaPackageFilter = new ModelicaPackageFilter();
	public ModelicaFilesStructure(){

	}
	public ModelicaFilesStructure(String path, String libraryName){
		resolveFileStructure(path, libraryName);
	}

	public void resolveFileStructure(String path, String libraryName){
		this.libraryName = libraryName;
		currentPackage = new PackageInfo(path, libraryName);
		packagesStack.push(currentPackage);
		tree.put(libraryName, currentPackage);
		getModelicaPaths(path, libraryName);
	}

	private void getModelicaPaths(String path, String packageName){
		File[] files = new File(path).listFiles();
		if (files == null){
			return;
		}
		for (File file: files){
			if (file.isDirectory()){
				if (modelicaPackageFilter.filterName(file.getPath())) {
					PackageInfo packageInfo = new PackageInfo(file.getPath(), currentPackage);
					updateCurrentPackage(packageInfo);
					String directoryPath = getSubpackagePath(packageName, file.getName());
					tree.get(packageName).addChild(packageInfo);
					tree.put(directoryPath, packageInfo);
					getModelicaPaths(file.getPath(), directoryPath);
				}
			}
			else if (file.getName().endsWith(".mo") && !file.getName().equals("package.mo")){
				ClassInfo classInfo = new ClassInfo(file.getPath(), currentPackage);
				tree.get(packageName).addChild(classInfo);
			}
		}
		returnToPreviousPackage();
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
