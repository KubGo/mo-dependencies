package dependencies.classesinfo;

import java.util.List;
import java.util.Map;

/**
 * Class dependencies interface for resolving the dependencies and searching through the tree
 */
public interface IClassDependencies {
    /**
     * @return List of other classes that are used in this one
     */
    List<String> getClasses();

    /**
     * @return list of extending classes of this class
     */
    List<String> getParentClasses();

    /**
     * Add parent classes of parent to have all extending classes inside
     *
     * @param parentClasses parent classes to add
     */
    void addParentClasses(List<String> parentClasses);

    /**
     * Sets component declarations for this class
     *
     * @param componentDeclarations parent component declarations
     */
    void setComponentDeclarations(Map<String, String> componentDeclarations);

    /**
     * Set classes
     *
     * @param classes that are used in this model
     */
    void setClasses(List<String> classes);

    /**
     * @param librariesNames name of libraries that are tested if they are resolved
     * @return true if all dependencies are resolved
     */
    boolean areParentDependenciesResolved(List<String> librariesNames);

    /**
     * @param librariesNames libraries to include as resolved
     */
    void setLibrariesResolved(List<String> librariesNames);

    /**
     * @return class dependencies instance that can be serialized and saved to file
     */
    IClassDependencies toSavableClassDependencies();

    /**
     * @return true if this  class is a model
     */
    boolean isModel();

    /**
     * @return Modelica path as String
     */
    String getModelicaPath();

    /**
     * @return return map with component declarations mapping
     */
    Map<String, String> getComponentDeclarations();

    List<String> getModifiedClasses();
}
