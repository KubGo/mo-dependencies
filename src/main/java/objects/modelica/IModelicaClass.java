package objects.modelica;

public interface IModelicaClass {
    String getName();
    String getPath();
    boolean pathMatches(String path);
    IModelicaClass getParent();
    boolean hasParent();

    boolean hasNext();

    IModelicaClass getNext();

    void reset();
}
