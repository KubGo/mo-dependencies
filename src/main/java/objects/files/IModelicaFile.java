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

    String getFilePath(String rootPath);
}
