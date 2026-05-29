package functionalities.classusage.classusagesearchers;

import dependencies.classesinfo.IClassDependencies;

import java.util.List;
import java.util.Map;

public interface IClassUsageSearcher {
    void searchForClassUsage(List<Map<String, ? extends IClassDependencies>> trees);

    List<Map<String, List<String>>> getExtendingClassesTrees();

    List<Map<String, List<String>>> getClassUsageTrees();

    List<List<String>> getTopLevelClasses();
}
