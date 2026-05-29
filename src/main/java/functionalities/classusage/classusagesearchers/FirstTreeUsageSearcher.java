package functionalities.classusage.classusagesearchers;

import dependencies.classesinfo.IClassDependencies;

import java.util.*;

public class FirstTreeUsageSearcher implements IClassUsageSearcher {

    private final Map<String, List<String>> classUsageTree = new TreeMap<>();
    private final Map<String, List<String>> extendingClassesTree = new TreeMap<>();
    List<Map<String, ? extends IClassDependencies>> searchTrees;
    private final Set<String> topLevelClasses = new HashSet<>();

    public FirstTreeUsageSearcher() {
    }

    @Override
    public void searchForClassUsage(List<Map<String, ? extends IClassDependencies>> trees) {
        searchTrees = trees.size() > 1 ? trees.subList(1, trees.size()) : trees;

    }

    @Override
    public List<Map<String, List<String>>> getExtendingClassesTrees() {
        return List.of(extendingClassesTree);
    }

    @Override
    public List<Map<String, List<String>>> getClassUsageTrees() {
        return List.of(classUsageTree);
    }

    @Override
    public List<List<String>> getTopLevelClasses() {
        return List.of(topLevelClasses.stream().toList());
    }

    /**
     * @param className name of the class to resolve where it is used inside the library
     */
    private void getClassUsage(String className) {
        classUsageTree.put(className, new ArrayList<>());
        extendingClassesTree.put(className, new ArrayList<>());
        for (var searchTree : searchTrees) {
            searchTree.forEach((
                    key, classDependencies) -> {
                if (classDependencies.getClasses().contains(className)) {
                    classUsageTree.get(className).add(key);
                }
                if (classDependencies.getParentClasses().contains(className)) {
                    extendingClassesTree.get(className).add(key);
                }
            });
        }

        if (classUsageTree.get(className).isEmpty() & extendingClassesTree.get(className).isEmpty()) {
            topLevelClasses.add(className);
        }
    }
}
