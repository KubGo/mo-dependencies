package dependencies.readdependencies;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonDependenciesReader extends AbstractDependenciesReader{
    public JsonDependenciesReader(String path, String suffix){
        this.path = path;
        this.extension = ".json";
        this.suffix = suffix;
    }
    public JsonDependenciesReader(String path){
        this(path, "_dependencies");
    }

    public Map<String, List<String>> readDependencies(){
        return Map.of();
    }

    public boolean checkExtension(){
        File file = new File(path);
        if (file.isFile()){
            return Arrays.stream(file.getName().split("\\."))
                    .toList().getLast().equals(extension.replace(".", ""));
        }
        String name = file.getName();
        file = new File(Path.of(path, name + suffix + extension).toUri());
        boolean fileExists = file.exists();
        if (fileExists) {
            path = file.getPath();
        }
        return fileExists;
    }
}
