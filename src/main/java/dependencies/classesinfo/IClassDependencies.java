package dependencies.classesinfo;

import java.util.List;

public interface IClassDependencies {
    List<String> getClasses();

    List<String> getParentClasses();

    void addClassesUsedByParents(List<String> parentClasses);

    boolean areParentDependenciesResolved(List<String> libraryName);

    void setLibrariesResolved(List<String> libraryName);
}
