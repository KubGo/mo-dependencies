package functionalities.classusage;

import dependencies.classesinfo.IClassDependencies;
import functionalities.classusage.classusagesearchers.FirstTreeUsageSearcher;
import functionalities.classusage.classusagesearchers.IClassUsageSearcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassUsageResolver {
    private final List<Map<String, ? extends IClassDependencies>> trees;
    IClassUsageSearcher classUsageSearcher;
    private List<List<String>> topLevelClassesList = new ArrayList<>();
    private List<Map<String, List<String>>> classUsageTreeList = new ArrayList<>();
    private List<Map<String, List<String>>> extendingClassesTreeList = new ArrayList<>();

    /**
     * @param trees trees for searching of usage. By default, only classes from first tree are searched
     *              if you wish to look for usage of classes in all trees in each other set searchAllTrees to true.
     */
    @SafeVarargs
    public ClassUsageResolver(Map<String, ? extends IClassDependencies>... trees) {
        this.trees = List.of(trees);
        setSearchAllTrees(false);

    }

    /**
     * @param searchAllTrees if true, search all trees for dependencies.
     *                       If false, search only first tree. By default,
     *                       only first tree is searched.
     */
    public void setSearchAllTrees(boolean searchAllTrees) {
        if (searchAllTrees) {
            throw new RuntimeException("Not implemented yet.");
        } else {
            classUsageSearcher = new FirstTreeUsageSearcher();
        }
    }

    /**
     * Searches for class usage in trees. If searchAllTrees set to true,
     * search for class usage of all trees else only for top tree.
     */
    public void search() {
        classUsageSearcher.searchForClassUsage(trees);
        topLevelClassesList = classUsageSearcher.getTopLevelClasses();
        extendingClassesTreeList = classUsageSearcher.getExtendingClassesTrees();
        classUsageTreeList = classUsageSearcher.getClassUsageTrees();
    }

    public List<List<String>> getTopLevelClassesList() {
        return topLevelClassesList;
    }

    public List<Map<String, List<String>>> getClassUsageTreeList() {
        return classUsageTreeList;
    }

    public List<Map<String, List<String>>> getExtendingClassesTreeList() {
        return extendingClassesTreeList;
    }
}
