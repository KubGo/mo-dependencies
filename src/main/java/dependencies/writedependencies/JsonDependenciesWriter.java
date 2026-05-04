package dependencies.writedependencies;


import com.google.gson.Gson;
import dependencies.DependencyTree;
import dependencies.classesinfo.ClassDependencies;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonDependenciesWriter extends AbstractDependenciesWriter {
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
		String file_name = Path.of(path, getFileName()).toString();
		File file = new File(file_name);

		if (file.exists()) {
			boolean deleted = file.delete();
			System.out.println(deleted ? "Deleted " + file_name : "Couldn't delete " + file_name);
		}
		fileWriter = new FileWriter(file_name, true);
		Gson gson = new Gson();

		List<ClassDependencies> classDependenciesList = new ArrayList<>();
		while (dependencies.hasNextClassDependencies()) {
			classDependenciesList.add(dependencies.getNextClassDependencies());
		}
		gson.toJson(classDependenciesList, fileWriter);

		fileWriter.close();
	}
}
