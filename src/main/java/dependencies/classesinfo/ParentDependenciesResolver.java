package dependencies.classesinfo;

import config.Config;

import java.util.*;

/**
 * Resolves parent dependencies from whole dependencies tree of library or multiple libraries.
 * Updates not finalized paths to absolute paths that might come from other
 * libraries and include their parent classes
 *
 * @param <T> {@link IClassDependencies} interface to use in this resolver
 */
public class ParentDependenciesResolver<T extends IClassDependencies> {
    List<Map<String, ? extends IClassDependencies>> trees = new ArrayList<>();

    Map<String, List<String>> resolvedParents = new TreeMap<>();
    private final List<String> librariesNames = new ArrayList<>();

    /**
     * @param trees libraries trees to resolve for dependencies.
     */
    @SafeVarargs
    public ParentDependenciesResolver(Map<String, ? extends IClassDependencies>... trees) {
        this.trees.addAll(List.of(trees));
        Arrays.stream(trees).forEach(this::addLibraryName);
    }

    private void addLibraryName(Map<String, ? extends IClassDependencies> tree) {
        String libraryName = Arrays.stream(tree.keySet().stream().limit(1).toList().getFirst().split("\\."))
                .toList()
                .getFirst()
                .split("\\.", 1)[0];
        librariesNames.add(libraryName);

    }

    public void addTreeForParentSearching(Map<String, ? extends IClassDependencies> tree) {
        trees.add(tree);
        addLibraryName(tree);
    }

    /**
     * @param treeToResolve tree to resolve the dependencies from other libraries specified
     *                      in this class
     * @return updated tree with included dependencies from parent classes
     */
    public Map<String, T> resolveParentDependencies(
            Map<String, T> treeToResolve) {
        treeToResolve.forEach(this::resolveParentDependencies);
        return treeToResolve;
    }

    /**
     * This class recursively adds classes that are used in parent classes to this class.
     * Results are saved in this instance so there's no need to resolve the same parent class
     * second time.
     * @param className name of the class
     * @param classDependencies classes dependencies instance for resolving used classes and parents
     * @return list of classes that are used in parents of this class
     */
    private List<String> resolveParentDependencies(String className, IClassDependencies classDependencies) {
        if (classDependencies.areParentDependenciesResolved(librariesNames)) {
            if (resolvedParents.containsKey(className)) {
                return resolvedParents.get(className);
            }
        }
        if (Config.VERBOSE) System.out.println("Resolving parent dependencies for " + className + "...");
        List<String> classesUsedByParents = new ArrayList<>();

        for (String parentName: classDependencies.getParentClasses()){
            if (resolvedParents.containsKey(parentName)) {
                classesUsedByParents.addAll(resolvedParents.get(parentName));
            } else {
                for (var tree : trees) {
                    if (tree.containsKey(parentName)) {
                        List<String> parentDependencies = resolveParentDependencies(parentName, tree.get(parentName));
                        classesUsedByParents.addAll(parentDependencies);
                    }
                }
            }
        }
        classDependencies.addClasses(classesUsedByParents);
        resolvedParents.put(className, classDependencies.getClasses());
        classDependencies.setLibrariesResolved(librariesNames);
        if (Config.VERBOSE) System.out.println("Resoled parent dependencies for" + className + ".");
        return classDependencies.getClasses();
    }

}
