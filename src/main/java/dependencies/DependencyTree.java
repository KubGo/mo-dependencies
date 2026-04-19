package dependencies;

import dependencies.classesinfo.ClassDependencies;
import dependencies.structureinfo.ClassInfo;
import files.ModelicaFileReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DependencyTree {
	ModelicaFilesStructure filesStructure = new ModelicaFilesStructure();
	ModelicaFileReader modelicaFileReader = new ModelicaFileReader();
	Map<String, ClassDependencies> dependenciesMap = new TreeMap<>();

	public DependencyTree(){

	}

	public void generateLibraryDependencies(
			String path,
			String libraryName){
		filesStructure.resolveFileStructure(path, libraryName);
		filesStructure.tree.forEach(
				(packageName, packageInfo) -> {
					List<ClassInfo> classInfoList = packageInfo.getClassDefinitions();
					classInfoList.forEach(classInfo -> {
						String modelicaPath = classInfo.getModelicaPath();
						String className = classInfo.getClassName();
						try {
							ClassDependencies classDependencies = new ClassDependencies(
									className,
									modelicaFileReader.readFile(
											classInfo.path));
							classDependencies.resolveInternalDependencies();
							dependenciesMap.put(
									modelicaPath, classDependencies);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					});
				});
	}

	public void resolveAbsolutePaths(){

	}



}
