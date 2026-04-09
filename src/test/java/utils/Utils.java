package utils;

import files.ModelicaFileReader;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Utils {
    public static String getModelicaTextFromResources(String resourceName) throws IOException {
        ModelicaFileReader modelicaFileReader = new ModelicaFileReader();
        String filePath = Objects.requireNonNull(
                Utils.class.getClassLoader().getResource(resourceName))
                .getPath();

        List<String> lines = modelicaFileReader.readFile(
                filePath);

        return String.join("\n", lines);
    }

}
