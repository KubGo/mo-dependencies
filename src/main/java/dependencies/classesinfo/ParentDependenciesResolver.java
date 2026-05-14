package dependencies.classesinfo;

import java.util.*;

public class ParentDependenciesResolver<T extends IClassDependencies> {
    List<Map<String, ? extends IClassDependencies>> trees = new ArrayList<>();

    Map<String, List<String>> resolvedParents = new TreeMap<>();
    private final List<String> librariesNames = new ArrayList<>();

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

    public Map<String, T> resolveParentDependencies(
            Map<String, T> treeToResolve) {
        treeToResolve.forEach(this::resolveParentDependencies);
        return treeToResolve;
    }

    private List<String> resolveParentDependencies(String className, IClassDependencies classDependencies) {
        if (classDependencies.areParentDependenciesResolved(librariesNames)) {
            if (resolvedParents.containsKey(className)) {
                return resolvedParents.get(className);
            }
        }
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
        classDependencies.addClassesUsedByParents(classesUsedByParents);
        resolvedParents.put(className, classDependencies.getClasses());
        classDependencies.setLibrariesResolved(librariesNames);
        return classDependencies.getClasses();
    }

}
