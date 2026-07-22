package objects.files;


public interface IModelicaFile {
    String getName();
    String getPath();
    boolean pathMatches(String path);

    ModelicaFolder getParent();
    boolean hasParent();

    boolean hasNext();

    IModelicaFile getNext();

    void reset();

    String getFilePath();

    void setFilePath(String rootPath);
}
