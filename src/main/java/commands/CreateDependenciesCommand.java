package commands;

import config.Config;
import dependencies.DependencyTreeResolver;
import dependencies.writedependencies.AbstractDependenciesWriter;
import dependencies.writedependencies.JsonDependenciesWriter;
import filtering.ModelicaLibraryFilter;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(name = "createDependencies", description = "Creates dependencies for library"

)
public class CreateDependenciesCommand implements Runnable {
	@CommandLine.Parameters
	private String libraryPath;
	@CommandLine.Option(names = {"--debug", "-d"})
	private boolean debug;
	@CommandLine.Option(names = {"--filter", "-f"}, arity = "1..*", defaultValue = CommandLine.Option.NULL_VALUE)
	private List<String> librariesToFilter;
	@CommandLine.Option(names = {"--libraries", "-l"}, arity = "1..*", defaultValue = CommandLine.Option.NULL_VALUE)
	private List<String> additionalLibraries;
	@CommandLine.Option(names = {"--writer", "-w"}, arity = "1..*", defaultValue = "json")
	private List<String> writers;

	public static void main(String[] args) {
		CommandLine.run(new CreateDependenciesCommand(), args);
	}

	@Override
	public void run() {
		if (debug) {
			Config.DEBUG = true;
		}
		if (Config.DEBUG) {
			System.out.println("Creating dependencies...");
		}
		if (librariesToFilter == null) {
			librariesToFilter = List.of();
		}
		List<ModelicaLibraryFilter> filters = librariesToFilter.stream().map(ModelicaLibraryFilter::new).toList();
		DependencyTreeResolver dependencyResolver = new DependencyTreeResolver(filters);
		if (additionalLibraries != null) {
			additionalLibraries.forEach(lib -> {
				DependencyTreeResolver additionalResolver = new DependencyTreeResolver(filters);
				additionalResolver.generateLibraryDependencies(lib);
				dependencyResolver.addLibraryDependencies(additionalResolver.getSimplifiedClassDependencies());
			});
		}
		dependencyResolver.generateLibraryDependencies(libraryPath);
		List<AbstractDependenciesWriter> dependenciesWriters = new ArrayList<>();
		for (String writer : writers) {
			if (writer.equals("json")) {
				dependenciesWriters.add(new JsonDependenciesWriter());
			}
			else {
				System.out.println("Unknown writer \"" + writer + "\", " + "for now available options are: \"json\"");
			}
		}
		dependenciesWriters.forEach(dependencyResolver::saveDependencies);
		if (Config.DEBUG) System.out.println("Dependencies created.");
	}
}
