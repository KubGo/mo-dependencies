package dependencies.classesinfo;

import java.util.List;

public interface IClassDependencies {
    List<String> getClasses();

    List<String> getParentClasses();

    void addClassesUsedByParents(List<String> parentClasses);

    boolean areParentDependenciesResolved(List<String> librariesNames);

    void setLibrariesResolved(List<String> librariesNames);

    IClassDependencies toClassDependencies();

    boolean isModel();

    String getModelicaPath();
}
