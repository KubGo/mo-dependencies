package commands;

import config.Config;
import dependencies.readdependencies.AbstractDependenciesReader;
import dependencies.readdependencies.JsonDependenciesReader;
import functionalities.classusage.ClassUsageResolver;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.List;

@CommandLine.Command(name = "getClassUsage", description = "Gets usage of library classes from generated libraries " + "dependencies.")
public class GetClassesUsageCommand implements Runnable {
	@CommandLine.Parameters
	private List<String> libraries;
	@CommandLine.Option(names = {"--debug", "-d"})
	private boolean debug;
	@CommandLine.Option(names = {"--verbose", "-v"})
	private boolean verbose;
	@CommandLine.Option(names = {"--reader", "-r"}, defaultValue = "json")
	private String readerName;
	@CommandLine.Option(names = {"--suffix", "-s"}, defaultValue = "_dependencies")
	private String suffix;

	public static void main(String[] args) {
		CommandLine.run(new GetClassesUsageCommand(), args);
	}


	@Override
	public void run() {
		if (debug) {
			Config.DEBUG = true;
		}
		if (verbose) {
			if (debug) {
				Config.VERBOSE = true;
			}
			else {
				System.out.println("Set also flag -d for debug to get verbose output.");
			}
		}

		AbstractDependenciesReader reader;
		if (readerName.equals("json")) {
			reader = new JsonDependenciesReader("library", suffix);
		}
		else {
			throw new RuntimeException(
					"Unknown file reader: \"" + readerName + "\"." + "For now available options are: " + Config.getAvailableExtensionsList() + ".");
		}

		ClassUsageResolver resolver = new ClassUsageResolver();

		for (String library : libraries) {
			try {
				reader.setPath(library);
				var tree = reader.readDependencies();
				resolver.addLibraryTree(tree);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		resolver.search();
		System.out.println("Top level classes:");
		System.out.println(String.join("\n", resolver.getTopLevelClassesList().getFirst()));
		System.out.println("Usage map:");
		System.out.println(resolver.getClassUsageTreeList());
		System.out.println("Extending classes map:");
		System.out.println(resolver.getExtendingClassesTreeList());

	}
}
