package io.read;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import objects.classes.IModelicaClass;
import objects.classes.ModelicaClass;
import objects.classes.ModelicaPackage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;

public class JsonPackageReader implements IReader {
    @Override
    public boolean fileReadable(Path path) {
        if (!path.toFile().exists()) {
            return false;
        }
        if (!path.toString().endsWith(".json")) {
            return false;
        }
        return path.toFile().canRead();
    }

    @Override
    public ModelicaPackage read(Path path) {
        if (!fileReadable(path)) {
            throw new RuntimeException("Couldn't read " + path + ".");
        }
        try {
            FileReader fileReader = new FileReader(path.toFile());
            JsonReader jsonReader = new JsonReader(fileReader);
            Type type = new TypeToken<ModelicaPackage>() {
            }
                    .getType();
            RuntimeTypeAdapterFactory<IModelicaClass> factory =
                    RuntimeTypeAdapterFactory.of(IModelicaClass.class, "type")
                            .registerSubtype(ModelicaPackage.class, "Modelica package")
                            .registerSubtype(ModelicaClass.class, "Modelica class");
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .enableComplexMapKeySerialization()
                    .registerTypeAdapterFactory(factory)
                    .create();
            ModelicaPackage modelicaPackage = gson.fromJson(jsonReader, type);
            modelicaPackage.setParents(null);
            return modelicaPackage;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
