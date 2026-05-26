package dependencies;

import config.Config;
import dependencies.classesinfo.ClassDependencies;
import dependencies.classesinfo.ClassDependenciesResolver;
import dependencies.classesinfo.IClassDependencies;
import dependencies.classesinfo.ParentDependenciesResolver;
import dependencies.structureinfo.ClassInfo;
import dependencies.writedependencies.AbstractDependenciesWriter;
import files.ModelicaFileReader;
import filtering.IFilter;
import modelica.pathresolvers.FileStructurePathResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Resolves dependency tree for the library
 */
public class DependencyTreeResolver {
	ModelicaFilesStructure filesStructure = new ModelicaFilesStructure();
	ModelicaFileReader modelicaFileReader = new ModelicaFileReader();
	private final ParentDependenciesResolver<ClassDependenciesResolver> parentDependenciesResolver = new ParentDependenciesResolver<>();
	private Map<String, ClassDependenciesResolver> dependencyTree = new TreeMap<>();
	private FileStructurePathResolver fileStructurePathResolver;
	private String libraryName = "";
	private String libraryPath;
	private final List<IFilter> filters = new ArrayList<>();

	/**
	 * @param filters filters to use on dependencies of each class
	 */
	public DependencyTreeResolver(List<? extends IFilter> filters) {
		filters.forEach(this::addFilter);
	}

	public DependencyTreeResolver() {
	}


	public Map<String, ClassDependenciesResolver> getDependencyTree() {
		return dependencyTree;
	}

	public void addFilter(IFilter filter) {
		filters.add(filter);
	}

	/**
	 * @param dependencies dependency tree of other library that might influence library
	 *                     dependencies.
	 */
	public void addLibraryDependencies(Map<String, ? extends IClassDependencies> dependencies) {
		parentDependenciesResolver.addTreeForParentSearching(dependencies);
	}

	/**
	 * @param path path to library top package
	 */
	public void generateLibraryDependencies(
			String path) {
		String libraryName = Path.of(path).getFileName().toString();
		generateLibraryDependencies(path, libraryName);
	}

	/**
	 * @param path path to library top package
	 * @param libraryName library name
	 */
	public void generateLibraryDependencies(
			String path,
			String libraryName){
		if (Config.DEBUG) System.out.println("Creating dependencies for " + libraryName + "...");
		this.libraryPath = path;
		this.libraryName = libraryName;
		filesStructure.resolveFileStructure(path, libraryName);
		fileStructurePathResolver = new FileStructurePathResolver(filesStructure.tree);
		if (Config.DEBUG) {
			if (!filters.isEmpty()) {
				System.out.println("Filters to apply:");
				filters.forEach(it -> System.out.println(it.getFilterDescription()));
			}
		}
		filesStructure.tree.forEach(
				(packageName, packageInfo) -> {
					List<ClassInfo> classInfoList = packageInfo.getClassDefinitions();
					classInfoList.forEach(classInfo -> {
						if (Config.VERBOSE) System.out.println("Resolving " + packageInfo.getModelicaPath() + "...");
						fileStructurePathResolver.setCurrentPackage(packageInfo.getModelicaPath());
						try {
							if (Config.VERBOSE) System.out.println("Resolving " + classInfo.getModelicaPath() + "...");
							resolveClassDependencies(classInfo);
							System.out.println(classInfo.getModelicaPath() + " resolved.");
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					});
				});
		parentDependenciesResolver.addTreeForParentSearching(dependencyTree);
		dependencyTree = parentDependenciesResolver.resolveParentDependencies(dependencyTree);
	}

	/**
	 * @param classInfo Class info for resolving the class dependencies
	 *
	 * @throws IOException when class cannot be read
	 */
	private void resolveClassDependencies(ClassInfo classInfo) throws IOException {
		ClassDependenciesResolver classDependenciesResolver = new ClassDependenciesResolver(
				classInfo.getClassName(), modelicaFileReader.readFile(classInfo.path));
		classDependenciesResolver.resolveInternalDependencies();
		classDependenciesResolver.resolveLibraryDependencies(fileStructurePathResolver);
		classDependenciesResolver.filter(filters);
		dependencyTree.put(classInfo.getModelicaPath(), classDependenciesResolver);
	}

	/**
	 * Include dependencies from other libraries in this tree.
	 * To add other dependencies see {@link #addLibraryDependencies(Map) addLibraryDependencies}
	 */
	public void includeParentsDependentClasses(){
		if (Config.DEBUG) System.out.println("Resolving parent dependencies for " + libraryName + "...");
		dependencyTree = parentDependenciesResolver.resolveParentDependencies(dependencyTree);
		if (Config.DEBUG) System.out.println("Resolved parent dependencies.");

	}

	/**
	 * @param className name of the class
	 *
	 * @return class dependencies for given class. If class name wasn't found null is returned.
	 */
	public IClassDependencies getClassDependencies(String className) {
		if (dependencyTree.containsKey(className)) {
			return dependencyTree.get(className).toSavableClassDependencies();
		} else {
			return null;
		}
	}

	/**
	 * @return Simplified class dependencies that can be serialized and saved to file.
	 */
	public TreeMap<String, ClassDependencies> getSimplifiedClassDependencies() {
		TreeMap<String, ClassDependencies> dependencies = new TreeMap<>();
		for (String key : dependencyTree.keySet()) {
			dependencies.put(key, dependencyTree.get(key).toSavableClassDependencies());
		}
		return dependencies;
	}

	/**
	 * @param writers Writer classes that extend {@link AbstractDependenciesWriter} and can save
	 *                {@link ClassDependencies} to file.
	 */
	public void saveDependencies(AbstractDependenciesWriter... writers) {
		if (Config.DEBUG) System.out.println("Saving dependencies...");
		for (AbstractDependenciesWriter writer : writers) {
			writer.setLibraryName(libraryName);
			writer.setPath(libraryPath);
			try {
				writer.writeDependencies(getSimplifiedClassDependencies());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (Config.DEBUG) System.out.println("Dependencies saved to: " + libraryPath + writer.getFileName());
		}
	}
}
