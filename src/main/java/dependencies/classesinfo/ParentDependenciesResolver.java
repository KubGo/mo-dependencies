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
    Map<String, Map<String, String>> resolvedParents = new TreeMap<>();
    Map<String, List<String>> parentExtendingClasses = new TreeMap<>();
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
    public Map<String, T> resolveParentComponents(
            Map<String, T> treeToResolve) {
        treeToResolve.forEach(this::resolveParentComponents);
        return treeToResolve;
    }

    /**
     * This class recursively adds classes that are used in parent classes to this class.
     * Results are saved in this instance so there's no need to resolve the same parent class
     * second time.
     * @param className name of the class
     * @param classDependencies classes dependencies instance for resolving used classes and parents
     * @return map of classes that are used in parents of this class
     */
    private Map<String, String> resolveParentComponents(String className, IClassDependencies classDependencies) {
        if (classDependencies.areParentDependenciesResolved(librariesNames)) {
            if (resolvedParents.containsKey(className)) {
                return resolvedParents.get(className);
            }
        }
        if (Config.VERBOSE) System.out.println("Resolving parent dependencies for " + className + "...");
        Map<String, String> componentsUsedByParents = new HashMap<>();

        for (String parentName: classDependencies.getParentClasses()){
            if (resolvedParents.containsKey(parentName)) {
                componentsUsedByParents.putAll(resolvedParents.get(parentName));
            } else {
                for (var tree : trees) {
                    if (tree.containsKey(parentName)) {
                        Map<String, String> parentDependencies = resolveParentComponents(
                                parentName,
                                tree.get(parentName));
                        componentsUsedByParents.putAll(parentDependencies);
                    }
                }
            }
        }
        parentExtendingClasses.put(className, classDependencies.getParentClasses());
        classDependencies.setComponentDeclarations(
                getUpdatedComponentDeclarations(classDependencies, componentsUsedByParents));
        classDependencies.setClasses(getClassesUsedByModel(classDependencies));

        var currentParents = List.copyOf(classDependencies.getParentClasses());

        for (var parent : currentParents) {
            classDependencies.addParentClasses(parentExtendingClasses.getOrDefault(parent, List.of()));
        }
        resolvedParents.put(className, classDependencies.getComponentDeclarations());
        classDependencies.setLibrariesResolved(librariesNames);
        if (Config.VERBOSE) System.out.println("Resoled parent dependencies for" + className + ".");
        return classDependencies.getComponentDeclarations();
    }

    private Map<String, String> getUpdatedComponentDeclarations(
            IClassDependencies classDependencies,
            Map<String, String> parentComponentDeclarations) {
        classDependencies.getComponentDeclarations().keySet().forEach(parentComponentDeclarations::remove);

        Map<String, String> updatedComponentDeclarations = new HashMap<>(Map.copyOf(parentComponentDeclarations));
        updatedComponentDeclarations.putAll(classDependencies.getComponentDeclarations());

        return updatedComponentDeclarations;
    }

    private List<String> getClassesUsedByModel(IClassDependencies classDependencies) {
        Set<String> classesFromComponents = new HashSet<>(classDependencies.getComponentDeclarations().values());
        classesFromComponents.addAll(classDependencies.getClasses());
        classDependencies.getParentClasses().forEach(parent -> {
            for (var tree : trees) {
                if (tree.containsKey(parent)) {
                    classesFromComponents.addAll(tree.get(parent).getClasses());
                }
            }
        });
        return classesFromComponents.stream().toList();
    }

}
