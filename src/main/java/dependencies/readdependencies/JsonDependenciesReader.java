package dependencies.readdependencies;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dependencies.classesinfo.ClassDependencies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class JsonDependenciesReader extends AbstractDependenciesReader{
    public JsonDependenciesReader(String path, String suffix){
        this.path = path;
        this.extension = ".json";
        this.suffix = suffix;
    }
    public JsonDependenciesReader(String path){
        this(path, "_dependencies");
    }

    public List<ClassDependencies> readDependencies() {
        Gson gson = new Gson();
        if (!checkExtension()) {
            throw new RuntimeException("Couldn't find the dependencies file at " + path);
        }
        try {
            FileReader fileReader = new FileReader(path);
            JsonReader jsonReader = new JsonReader(fileReader);
            ClassDependencies[] dependencies = gson.fromJson(jsonReader, ClassDependencies[].class);
            return List.of(dependencies);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
