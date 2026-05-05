package dependencies.classesinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ParentDependenciesResolver {
    List<TreeMap<String, ClassDependenciesResolver>> tree;

    TreeMap<String, List<String>> resolvedDependencies;

    @SafeVarargs
    public ParentDependenciesResolver(TreeMap<String, ClassDependenciesResolver>... tree){
        this.tree = List.of(tree);
    }

    public TreeMap<String, ClassDependenciesResolver> resolveParentDependencies(TreeMap<String, ClassDependenciesResolver> treeToResolve){
        TreeMap<String, ClassDependenciesResolver> resolvedClasses = new TreeMap<>();
        treeToResolve.forEach((key, classDep) -> {

        });
    }

    private List<String> resolveParentDependencies(String className, ClassDependenciesResolver classDependencies){
        if (classDependencies.isParentDependenciesResolved()){
            if (resolvedDependencies.containsKey(className)){
                return resolvedDependencies.get(className);
            }
        }
        List<String> dependenciesToAdd = new ArrayList<>();
        for (String parentName: classDependencies.getParentClasses()){
            if (resolvedDependencies.containsKey(parentName)){
                dependenciesToAdd.addAll(resolvedDependencies.get(parentName));
            } else {
                // TODO("Finish this method")
            }
        }

    }


}
