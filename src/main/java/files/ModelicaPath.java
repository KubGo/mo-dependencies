package files;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ModelicaPath {
    String filePath;
    String path;
    String className;

    public ModelicaPath(String filePath) {
        this(filePath, "");
    }

    public ModelicaPath(String filePath, String libraryName){
        String separator = File.separator;
        if (libraryName.isBlank()){
            this.filePath = filePath;
        } else {
            var parts = Arrays.stream(filePath.split(Pattern.quote(separator))).toList();
            int skipNumber = parts.indexOf(libraryName);
            this.filePath = String.join(separator, parts.stream().skip(skipNumber).toList());
        }
        path = convertToModelicaPath(this.filePath, separator);
        className = getClassName(path);
    }

    private String convertToModelicaPath(String path, String separator){
        String modelicaPath = String.join(".", this.filePath.split(Pattern.quote(separator)));
        int indexOfExtension = path.indexOf(".mo");
        modelicaPath = indexOfExtension > 0 ? modelicaPath.substring(0, indexOfExtension) : modelicaPath;
        return modelicaPath;
    }

    private String getClassName(String modelicaPath){
        return modelicaPath.substring(modelicaPath.lastIndexOf('.') + 1);
    }

}
