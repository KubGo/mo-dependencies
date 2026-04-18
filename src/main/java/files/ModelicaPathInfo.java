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
        } else {
            this.filePath = file.toPath().toString().substring(0, libraryNameIndex + 1);
        }
        path = convertToModelicaPath(file.toPath());
        className = getClassName(path);
        this.libraryName = (libraryName.isBlank()) ? libraryName : getLibraryName(path);
    }

    private String convertToModelicaPath(Path path){
        StringBuilder sb = new StringBuilder();
        boolean first = false;
        if (!libraryName.isBlank()){
            sb.append(libraryName);
        } else{
            first = true;
        }
		for (Path name : path) {
			if (first) {
				if (sb.isEmpty()) {
					sb.append(name);
				}
			    else {
					sb.append(".").append(name);
				}
			}
		    else {
				first = name.toString().equals(libraryName);
			}
		}
        return sb.toString().replace(".mo", "");
    }

    private String getClassName(String modelicaPath){
        return modelicaPath.substring(modelicaPath.lastIndexOf('.') + 1);
    }

    private String getLibraryName(String modelicaPath){
        int endIndex = modelicaPath.indexOf(".");
        return (endIndex > 0) ? modelicaPath.substring(0, endIndex) : modelicaPath;
    }

}
