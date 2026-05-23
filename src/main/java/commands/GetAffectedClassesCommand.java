package commands;

import config.Config;
import dependencies.classesinfo.ClassDependencies;
import dependencies.readdependencies.AbstractDependenciesReader;
import dependencies.readdependencies.JsonDependenciesReader;
import functionalities.affectedclasses.AffectedClassesResolver;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@CommandLine.Command(name = "getAffectedClasses", description = "Get classes affected by list of changes classes.")
public class GetAffectedClassesCommand implements Runnable {
	@CommandLine.Option(names = {"--debug", "-d"})
	private boolean debug;
	@CommandLine.Option(names = {"--classes", "-c"}, required = true)
	private List<String> classes;
	@CommandLine.Option(names = {"--libraries", "-l"}, required = true)
	private List<String> libraries;
	@CommandLine.Option(names = {"--reader", "-r"}, defaultValue = "json")
	private String readerName;
	@CommandLine.Option(names = {"--suffix", "-s"}, defaultValue = "_dependencies")
	private String suffix;
	@CommandLine.Option(names = {"--no-child-classes", "-n"})
	private boolean noChildrenClasses;

	public static void main(String[] args) {
		CommandLine.run(new GetAffectedClassesCommand(), args);
	}

	@Override
	public void run() {
		if (debug) {
			Config.DEBUG = true;
		}
		if (Config.DEBUG) System.out.println("Retrieving affected classes...");

		String lib = libraries.getFirst();
		AffectedClassesResolver<ClassDependencies> affectedClassesResolver = getAffectedClassesResolver(lib);
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(classes);
		if (Config.DEBUG) {
			System.out.println("Affected classes retrieved.");
		}
		System.out.println(String.join("\n", affectedClasses));
	}

	private AffectedClassesResolver<ClassDependencies> getAffectedClassesResolver(String lib) {
		AbstractDependenciesReader reader;
		AffectedClassesResolver<ClassDependencies> affectedClassesResolver = new AffectedClassesResolver<>();
		affectedClassesResolver.setIncludeChildrenClasses(!noChildrenClasses);
		if (readerName.equals("json")) {
			reader = new JsonDependenciesReader(lib, suffix);
		}
		else {
			throw new RuntimeException(
					"Unknown file reader: \"" + readerName + "\"." + "For now available options are: " + Config.getAvailableExtensionsList() + ".");
		}
		try {
			Map<String, ClassDependencies> tree = reader.readDependencies();
			affectedClassesResolver.addDependencyTree(tree);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		return affectedClassesResolver;
	}
}
