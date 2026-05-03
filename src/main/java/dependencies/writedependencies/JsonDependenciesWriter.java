package dependencies.writedependencies;


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
	public void writeDependencies(DependencyTree dependencies) throws IOException {
		FileWriter fileWriter;
		String file_name = Paths.get(path, getFileName()).toString();
		File file = new File(file_name);
		if (file.exists()) {
			boolean deleted = file.delete();
			System.out.println(deleted ? "Deleted " + file_name : "Couldn't delete " + file_name);
		}
		fileWriter = new FileWriter(file_name, true);
		fileWriter.write("[");

		dependencies.reset();
		if (dependencies.hasNextClassDependencies()){
			fileWriter.write(dependencies.getNextClassDependencies().toString());
		}
		while (dependencies.hasNextClassDependencies()) {
			fileWriter.write(", ");
			fileWriter.write(dependencies.getNextClassDependencies().toString());
		}
		fileWriter.write("]");
		fileWriter.close();
	}
}
