package dependencies.readdependencies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import config.Config;
import dependencies.classesinfo.ClassDependencies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Reads dependencies from json file
 */
public class JsonDependenciesReader extends AbstractDependenciesReader{
    public JsonDependenciesReader(String path, String suffix){
        this.path = path;
        this.extension = ".json";
        this.suffix = suffix;
    }
    public JsonDependenciesReader(String path){
        this(path, "_dependencies");
    }

    /**
     * Reads dependencies from json file
     *
     * @return dependencies tree saved in file
     */
    @Override
    public Map<String, ClassDependencies> readDependencies() throws FileNotFoundException {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        if (!checkExtension()) {
            throw new FileNotFoundException("Couldn't find the dependencies file at " + path);
        }
        try {
            if (Config.DEBUG) System.out.println("Reading dependencies from " + path + "...");
            FileReader fileReader = new FileReader(path);
            JsonReader jsonReader = new JsonReader(fileReader);
            Type type = new TypeToken<TreeMap<String, ClassDependencies>>() {
            }.getType();
            Map<String, ClassDependencies> dependencies = gson.fromJson(jsonReader, type);
            if (Config.DEBUG) System.out.println("Dependencies read.");
            return dependencies;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return true if extension matches and file exists
     */
    @Override
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
