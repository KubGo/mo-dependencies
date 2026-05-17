package files;

import java.nio.file.Path;

/**
 * Checker for Modelica file types
 */
public class FileTypeChecker {


    /**
     * @param path path to file
     * @return file type
     */
    public FileType getFileType(Path path){
        String extension = getExtension(path);
        String orderExtension = "order";
        String modelicaExtension = "mo";
        if (extension.equals(orderExtension)){
            return FileType.ORDER;
        } else if (extension.equals(modelicaExtension)){
            String packageFileName = "package.mo";
            if (path.getFileName().toString().equals(packageFileName)){
                return FileType.PACKAGE;
            } else {
                return FileType.MODEL;
            }
        } else {
            return FileType.OTHER;
        }
    }

    private String getExtension(Path path){
        String filename = path.getFileName().toString();
        int index = filename.lastIndexOf('.');
        if (index <= 0 || index == filename.length() - 1){
            return "";
        }
        return filename.substring(index + 1);
    }
}
