package io.write;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.Config;
import objects.classes.ModelicaPackage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonWriter implements IWriter {

    @Override
    public void save(ModelicaPackage library, Path path) {
        FileWriter fileWriter;
        File file = Paths.get(path.toString(), library.getClassName() + ".json").toFile();
        if (file.exists()) {
            boolean deleted = file.delete();
            if (Config.DEBUG) {
                if (deleted) {
                    System.out.println("Deleted " + file.getName() + "...");
                } else {
                    System.out.println("Couldn't delete " + file.getName());
                }
            }
            if (!deleted) {
                throw new RuntimeException("Couldn't delete " + file.getName());
            }
        }
        try {
            fileWriter = new FileWriter(file, true);
            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .setPrettyPrinting()
                    .enableComplexMapKeySerialization()
                    .create();
            if (Config.DEBUG) {
                System.out.println("Writing library to " + file.getName() + "...");
            }
            gson.toJson(library, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
