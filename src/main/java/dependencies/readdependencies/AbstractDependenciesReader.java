package dependencies.readdependencies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractDependenciesReader {
    protected String extension;
    protected String path;
    protected String suffix;
    protected String libraryName;

    protected String readFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try(FileReader fileReader = new FileReader(path)){
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
        }
        return String.join("", lines);
    }
}
