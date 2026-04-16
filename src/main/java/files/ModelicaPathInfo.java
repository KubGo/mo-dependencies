package files;

import java.io.File;
import java.util.regex.Pattern;

public class ModelicaPathInfo {
    public String filePath;
    public String path;
    public String className;
    public String libraryName;

    public ModelicaPathInfo(String filePath) {
        this(filePath, "");
    }

    public ModelicaPathInfo(String filePath, String libraryName){
		File file = new File(filePath);
        int libraryNameIndex = file.getPath().indexOf(libraryName);
        String separator = File.pathSeparator;
        if (libraryName.isBlank()){
            this.filePath = filePath;
        } else {
//            var parts = Arrays.stream(filePath.split(Pattern.quote(separator))).toList();
//            int skipNumber = parts.indexOf(libraryName);
            this.filePath = file.toPath().toString().substring(0, libraryNameIndex + 1);
//            this.filePath = String.join(separator, parts.stream().skip(skipNumber).toList());
        }
        path = convertToModelicaPath(this.filePath, separator);
        className = getClassName(path);
        this.libraryName = libraryName;
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

    private String getLibraryName(String modelicaPath){
        int index =  modelicaPath.indexOf(".");
        return (index > 0) ? modelicaPath.substring(0, index) : modelicaPath;
    }

}
