package dependencies.readdependencies;

import dependencies.classesinfo.ClassDependencies;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Abstract class that defines reading the depencies from file
 */
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

    /**
     * Reads dependencies from file
     *
     * @return map of class dependencies
     */
    abstract Map<String, ClassDependencies> readDependencies() throws FileNotFoundException;

    /**
     * Check if extension matches
     *
     * @return true if matches
     */
    abstract boolean checkExtension();
}
