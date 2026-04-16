package dependencies;

import dependencies.structureinfo.ClassInfo;
import dependencies.structureinfo.PackageInfo;

import java.io.File;
import java.util.Stack;
import java.util.TreeMap;

public class ModelicaFilesStructure {
	private PackageInfo currentPackage;
	private Stack<PackageInfo> packagesStack = new Stack<>();
	TreeMap<String, PackageInfo> tree = new TreeMap<>();
	private final String libraryName;
	public ModelicaFilesStructure(String path, String libraryName){
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
				PackageInfo packageInfo = new PackageInfo(file.getPath(), currentPackage);
				packagesStack.push(packageInfo);
				String directoryPath = getSubpackagePath(packageName, file.getName());
				tree.get(packageName).addChild(packageInfo);
				tree.put(directoryPath, packageInfo);
				getModelicaPaths(
						file.getPath(), directoryPath);
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

	private String getSubpackagePath(String packageName, String fileName){
		return packageName + "." + fileName;
	}

}
