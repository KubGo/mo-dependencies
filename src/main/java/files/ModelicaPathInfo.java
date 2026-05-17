package files;

import java.io.File;
import java.nio.file.Path;


/**
 * Class containing information about the Modelica class like its name,
 * full path in Modelica and file system
 */
public class ModelicaPathInfo {
    public String filePath;
    public String path;
    public String className;
    public String libraryName;

    /**
     * @param filePath Relative path to the Modelica file that starts with
     *                 top level library directory
     */
    public ModelicaPathInfo(String filePath) {
        this(filePath, "");
    }


    /**
     * @param filePath    path to the Modelica file, can be absolute path
     * @param libraryName name of the library and its top level directory
     */
    public ModelicaPathInfo(String filePath, String libraryName){
		File file = new File(filePath);
        this.libraryName = libraryName;
        int libraryNameIndex = file.getPath().indexOf(libraryName);
        if (libraryName.isBlank()){
            this.filePath = filePath;
        } else {
            this.filePath = file.toPath().toString().substring(0, libraryNameIndex + 1);
        }
        path = convertToModelicaPath(file.toPath());
        className = getClassName(path);
        this.libraryName = (libraryName.isBlank()) ? libraryName : getLibraryName(path);
    }

    /**
     * @param path file path to the files
     * @return Modelica path to this file
     */
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

    /**
     * @param modelicaPath Modelica path to the class
     * @return class name of that file
     */
    private String getClassName(String modelicaPath){
        return modelicaPath.substring(modelicaPath.lastIndexOf('.') + 1);
    }

    /**
     * @param modelicaPath absolute Modelica path to the class
     * @return library name
     */
    private String getLibraryName(String modelicaPath){
        int endIndex = modelicaPath.indexOf(".");
        return (endIndex > 0) ? modelicaPath.substring(0, endIndex) : modelicaPath;
    }

}
