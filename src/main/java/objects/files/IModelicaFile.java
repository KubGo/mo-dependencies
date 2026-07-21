package objects.files;

public interface IModelicaFile {
    String getName();
    String getPath();
    boolean pathMatches(String path);

    IModelicaFile getParent();
    boolean hasParent();

    boolean hasNext();

    IModelicaFile getNext();

    void reset();

    void resetAll();

    String getFilePath();

    void setFilePath(String rootPath);
}
