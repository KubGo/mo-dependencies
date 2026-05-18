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

		String lib = libraries.getFirst();
		AbstractDependenciesReader reader = new JsonDependenciesReader(lib, suffix);
		AffectedClassesResolver<ClassDependencies> affectedClassesResolver = new AffectedClassesResolver<>();
		affectedClassesResolver.setIncludeChildrenClasses(!noChildrenClasses);
		if (readerName.equals("json")) {
			reader = new JsonDependenciesReader(lib, suffix);
		}
		try {
			Map<String, ClassDependencies> tree = reader.readDependencies();
			affectedClassesResolver.addDependencyTree(tree);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		List<String> affectedClasses = affectedClassesResolver.getAffectedClasses(classes);
		if (Config.DEBUG) {
			affectedClasses.forEach(System.out::println);
		}
	}
}
