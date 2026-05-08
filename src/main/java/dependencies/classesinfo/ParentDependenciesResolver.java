package dependencies.classesinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class ParentDependenciesResolver {
    List<TreeMap<String, ClassDependenciesResolver>> trees = new ArrayList<>();

    TreeMap<String, List<String>> resolvedParents = new TreeMap<>();
    private final List<String> librariesNames = new ArrayList<>();

    @SafeVarargs
    public ParentDependenciesResolver(TreeMap<String, ClassDependenciesResolver>... trees) {
        this.trees.addAll(List.of(trees));
        Arrays.stream(trees).forEach(this::addLibraryName);
    }

    private void addLibraryName(TreeMap<String, ClassDependenciesResolver> tree) {
        String libraryName = Arrays.stream(tree.keySet().stream().limit(1).toList().getFirst().split("\\."))
                .toList()
                .getFirst()
                .split("\\.", 1)[0];
        librariesNames.add(libraryName);

    }

    public void addTreeForParentSearching(TreeMap<String, ClassDependenciesResolver> tree) {
        trees.add(tree);
    }

    public TreeMap<String, ClassDependenciesResolver> resolveParentDependencies(TreeMap<String, ClassDependenciesResolver> treeToResolve){
        treeToResolve.forEach(this::resolveParentDependencies);
        return treeToResolve; // Check if this works and I do not have to reassign the values there
    }

    private List<String> resolveParentDependencies(String className, ClassDependenciesResolver classDependencies){
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
