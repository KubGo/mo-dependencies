package dependencies;

import files.ModelicaPath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ModelicaFilesStructure {
	TreeMap<String, List<String>> tree = new TreeMap<>();
	Boolean pastFiles = false;
	private final String libraryName;
	public ModelicaFilesStructure(String path, String libraryName){
		this.libraryName = libraryName;
		tree.put(libraryName, new ArrayList<>());
		getModelicaPaths(path, libraryName);
	}

	private void getModelicaPaths(String path, String packageName){
		File[] files = new File(path).listFiles();
		if (files == null){
			return;
		}
		pastFiles = true;
		for (File file: files){
			if (file.isDirectory()){
				String directoryPath = getSubpackagePath(packageName, file.getName());
				tree.get(packageName).add(file.getName());
				tree.put(directoryPath, new ArrayList<>());
				getModelicaPaths(
						file.getPath(), directoryPath
						);
			}
			else if (file.getName().endsWith(".mo") && !file.getName().equals("package.mo")){
				ModelicaPath modelicaPath = new ModelicaPath(path, "");
				tree.get(packageName).add(file.getName());
			}
		}
	}

	private String getSubpackagePath(String packageName, String fileName){
		return packageName + "." + fileName;
	}

}
