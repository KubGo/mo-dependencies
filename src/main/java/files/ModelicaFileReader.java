package files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ModelicaFileReader {
    public String readFile(String path) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (FileReader fileReader = new FileReader(path)) {
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
            reader.close();
        }
        return String.join("\n", lines);
    }
}
