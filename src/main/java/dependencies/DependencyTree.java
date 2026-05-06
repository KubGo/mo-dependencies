package dependencies;

import dependencies.classesinfo.ClassDependencies;
import dependencies.classesinfo.ClassDependenciesResolver;
import dependencies.classesinfo.ParentDependenciesResolver;
import dependencies.structureinfo.ClassInfo;
import dependencies.writedependencies.AbstractDependenciesWriter;
import files.ModelicaFileReader;
import modelica.pathresolvers.FileStructurePathResolver;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

public class DependencyTree {
	ModelicaFilesStructure filesStructure = new ModelicaFilesStructure();
	ModelicaFileReader modelicaFileReader = new ModelicaFileReader();
	private final ParentDependenciesResolver parentDependenciesResolver = new ParentDependenciesResolver();
	TreeMap<String, ClassDependenciesResolver> dependenciesMap = new TreeMap<>();
	private FileStructurePathResolver fileStructurePathResolver;
	private String libraryName = "";
	private String libraryPath;

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
							ClassDependenciesResolver classDependenciesResolver = new ClassDependenciesResolver(
									className,
									modelicaFileReader.readFile(
											classInfo.path));
							classDependenciesResolver.resolveInternalDependencies();
							classDependenciesResolver.resolveLibraryDependencies(fileStructurePathResolver);
							dependenciesMap.put(modelicaPath, classDependenciesResolver);
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					});
					parentDependenciesResolver.addTreeForParentSearching(dependenciesMap);
				});
	}

	public void includeParentsDependentClasses(){
		dependenciesMap = parentDependenciesResolver.resolveParentDependencies(dependenciesMap);

	}

	public ClassDependencies getClassDependencies(String className) {
		if (dependenciesMap.containsKey(className)) {
			return dependenciesMap.get(className).toClassDependencies();
		} else {
			return null;
		}
	}

	public List<ClassDependencies> getAllClassDependencies() {
		return dependenciesMap.values().stream().map(ClassDependenciesResolver::toClassDependencies).toList();
	}

	public void saveDependencies(AbstractDependenciesWriter... writers) {
		for (AbstractDependenciesWriter writer : writers) {
			writer.setLibraryName(libraryName);
			writer.setPath(libraryPath);
			try {
				writer.writeDependencies(this);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
