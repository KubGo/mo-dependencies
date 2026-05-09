package dependencies.classesinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class ParentDependenciesResolver<T extends IClassDependencies> {
    List<TreeMap<String, T>> trees = new ArrayList<>();

    TreeMap<String, List<String>> resolvedParents = new TreeMap<>();
    private final List<String> librariesNames = new ArrayList<>();

    @SafeVarargs
    public ParentDependenciesResolver(TreeMap<String, T>... trees) {
        this.trees.addAll(List.of(trees));
        Arrays.stream(trees).forEach(this::addLibraryName);
    }

    private void addLibraryName(TreeMap<String, T> tree) {
        String libraryName = Arrays.stream(tree.keySet().stream().limit(1).toList().getFirst().split("\\."))
                .toList()
                .getFirst()
                .split("\\.", 1)[0];
        librariesNames.add(libraryName);

    }

    public void addTreeForParentSearching(TreeMap<String, T> tree) {
        trees.add(tree);
    }

    public TreeMap<String, T> resolveParentDependencies(TreeMap<String, T> treeToResolve) {
        treeToResolve.forEach(this::resolveParentDependencies);
        return treeToResolve;
    }

    private List<String> resolveParentDependencies(String className, T classDependencies) {
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
