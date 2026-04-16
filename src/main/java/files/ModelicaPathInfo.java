package files;

import java.io.File;
import java.nio.file.Path;


public class ModelicaPathInfo {
    private int libraryNameIndex;
    public String filePath;
    public String path;
    public String className;
    public String libraryName;

    public ModelicaPathInfo(String filePath) {
        this(filePath, "");
    }


    public ModelicaPathInfo(String filePath, String libraryName){
		File file = new File(filePath);
        this.libraryName = libraryName;
        libraryNameIndex = file.getPath().indexOf(libraryName);
        String separator = File.pathSeparator;
        if (libraryName.isBlank()){
            this.filePath = filePath;
            this.libraryName = file.toPath().getFileName().toString();
        } else {
            this.filePath = file.toPath().toString().substring(0, libraryNameIndex + 1);
        }
        path = convertToModelicaPath(file.toPath());
        className = getClassName(path);
    }

    private String convertToModelicaPath(Path path){
        StringBuilder sb = new StringBuilder();
        sb.append(libraryName);
        var iterator = path.iterator();
        boolean first = false;
        while (iterator.hasNext()){
            var name = iterator.next();
            if (first){
                sb.append(".").append(name);
            } else {
                first = name.toString().equals(libraryName);
            }
        }
        return sb.toString().replace(".mo", "");
    }

    private String getClassName(String modelicaPath){
        return modelicaPath.substring(modelicaPath.lastIndexOf('.') + 1);
    }

}
