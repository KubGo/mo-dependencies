package dependencies.writedependencies;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dependencies.classesinfo.ClassDependencies;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeMap;

public class JsonDependenciesWriter extends AbstractDependenciesWriter {
	public JsonDependenciesWriter(String suffix) {
		extension = ".json";
		this.suffix = suffix;
	}

	public JsonDependenciesWriter() {
		this("_dependencies");
	}

	@Override
	public void writeDependencies(TreeMap<String, ClassDependencies> dependencies) throws IOException {
		FileWriter fileWriter;
		String file_name = Path.of(path, getFileName()).toString();
		File file = new File(file_name);

		if (file.exists()) {
			boolean deleted = file.delete();
			System.out.println(deleted ? "Deleted " + file_name : "Couldn't delete " + file_name);
		}
		fileWriter = new FileWriter(file_name, true);
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().enableComplexMapKeySerialization().create();

		gson.toJson(dependencies, fileWriter);
		fileWriter.close();
	}
}
