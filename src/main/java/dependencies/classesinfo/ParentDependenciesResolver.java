package dependencies.classesinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ParentDependenciesResolver {
    List<TreeMap<String, ClassDependenciesResolver>> trees;

    TreeMap<String, List<String>> resolvedParents = new TreeMap<>();

    @SafeVarargs
    public ParentDependenciesResolver(TreeMap<String, ClassDependenciesResolver>... trees) {
        this.trees = List.of(trees);
    }

    public TreeMap<String, ClassDependenciesResolver> resolveParentDependencies(TreeMap<String, ClassDependenciesResolver> treeToResolve){
        treeToResolve.forEach(this::resolveParentDependencies);
        return treeToResolve; // Check if this works and I do not have to reassign the values there
    }

    private List<String> resolveParentDependencies(String className, ClassDependenciesResolver classDependencies){
        if (classDependencies.isParentDependenciesResolved()){
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
        resolvedParents.put(className, classDependencies.getAbsolutePathsClassList());
        return classDependencies.getAbsolutePathsClassList();
    }

}
