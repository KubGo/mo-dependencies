package commands;

import config.Config;
import dependencies.DependencyTreeResolver;
import dependencies.writedependencies.JsonDependenciesWriter;
import picocli.CommandLine;

@CommandLine.Command(name = "createDependencies", description = "Creates dependencies for library"

)
public class CreateDependenciesCommand implements Runnable {
	@CommandLine.Option(names = {"--library", "-l"})
	private String libraryPath;
	@CommandLine.Option(names = {"--debug", "-d"})
	private boolean debug;

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
		DependencyTreeResolver dependencyResolver = new DependencyTreeResolver();
		dependencyResolver.generateLibraryDependencies(libraryPath);
		JsonDependenciesWriter writer = new JsonDependenciesWriter();
		dependencyResolver.saveDependencies(writer);
		if (Config.DEBUG) System.out.println("Dependencies created.");
	}
}
