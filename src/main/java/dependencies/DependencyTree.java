package dependencies;

import dependencies.classesinfo.ClassDependencies;
import dependencies.classesinfo.DependenciesRecord;
import dependencies.structureinfo.ClassInfo;
import dependencies.writedependencies.IDependenciesWriter;
import files.ModelicaFileReader;
import modelica.pathresolvers.FileStructurePathResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DependencyTree {
	ModelicaFilesStructure filesStructure = new ModelicaFilesStructure();
	ModelicaFileReader modelicaFileReader = new ModelicaFileReader();
	Map<String, ClassDependencies> dependenciesMap = new TreeMap<>();
	private FileStructurePathResolver fileStructurePathResolver;
	private String libraryName = "";
	private String libraryPath;

	private int currentPathIndex = 0;
	private List<String> keys = new ArrayList<>();

	public DependencyTree(){

	}

	public void generateLibraryDependencies(
			String path,
			String libraryName){
		this.libraryPath = path;
		this.libraryName = libraryName;
		filesStructure.resolveFileStructure(path, libraryName);
		fileStructurePathResolver = new FileStructurePathResolver(filesStructure.tree);

		filesStructure.tree.forEach(
				(packageName, packageInfo) -> {
					List<ClassInfo> classInfoList = packageInfo.getClassDefinitions();
					classInfoList.forEach(classInfo -> {
						fileStructurePathResolver.setCurrentPackage(packageInfo);
						String modelicaPath = classInfo.getModelicaPath();
						String className = classInfo.getClassName();
						try {
							ClassDependencies classDependencies = new ClassDependencies(
									className,
									modelicaFileReader.readFile(
											classInfo.path));
							classDependencies.resolveInternalDependencies();
							classDependencies.resolveLibraryDependencies(fileStructurePathResolver);
							dependenciesMap.put(
									modelicaPath, classDependencies);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					});
				});
		keys = new ArrayList<>(dependenciesMap.keySet());
	}

	public DependenciesRecord getNextClassDependencies() {
		currentPathIndex++;
		return dependenciesMap.get(keys.get(currentPathIndex)).toDependenciesRecord();
	}

	public boolean hasNextClassDependencies() {
		return currentPathIndex + 1 < keys.size();
	}

	public void reset() {
		currentPathIndex = 0;
	}

	public void saveDependencies(IDependenciesWriter... writers) {
		for (IDependenciesWriter writer : writers) {
			writer.setLibraryName(libraryName);
		}
	}
}
