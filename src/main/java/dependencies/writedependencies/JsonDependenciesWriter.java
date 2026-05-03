package dependencies.writedependencies;

import com.google.gson.Gson;
import dependencies.DependencyTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class JsonDependenciesWriter extends IDependenciesWriter {
	public JsonDependenciesWriter(String suffix) {
		extension = ".json";
		this.suffix = suffix;
	}

	public JsonDependenciesWriter() {
		this("_dependencies");
	}

	@Override
	public void writeDependencies(DependencyTree dependencies) {
		Gson gson = new Gson();
		FileWriter fileWriter;
		File file = new File(path);

		try {
			fileWriter = new FileWriter(Paths.get(file.toPath().toString(), getFileName()).toString());
		} catch (IOException e) {
			throw new RuntimeException("Couldn't write to a file");
		}

		dependencies.reset();
		while (dependencies.hasNextClassDependencies()) {
			gson.toJson(dependencies.getNextClassDependencies(), fileWriter);
		}
	}
}
