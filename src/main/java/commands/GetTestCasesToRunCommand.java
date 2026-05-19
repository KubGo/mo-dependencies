package commands;

import config.Config;
import dependencies.classesinfo.ClassDependencies;
import dependencies.readdependencies.AbstractDependenciesReader;
import dependencies.readdependencies.JsonDependenciesReader;
import functionalities.affectedclasses.AffectedClassesResolver;
import functionalities.tests.TestCasesResolver;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@CommandLine.Command(name = "getTestCases", description = "Get test cases to run for affected classes")
public class GetTestCasesToRunCommand implements Runnable {
	@CommandLine.Option(names = {"--debug", "-d"})
	private boolean debug;
	@CommandLine.Option(names = {"--classes", "-c"}, required = true)
	private List<String> classes;
	@CommandLine.Option(names = {"--test-library", "-t"}, required = true)
	private String testLibrary;
	@CommandLine.Option(names = {"--libraries", "-l"})
	private List<String> libraries;
	@CommandLine.Option(names = {"--reader", "-r"}, defaultValue = "json")
	private String readerName;
	@CommandLine.Option(names = {"--suffix", "-s"}, defaultValue = "_dependencies")
	private String suffix;
	@CommandLine.Option(names = {"--no-child-classes", "-n"})
	private boolean noChildrenClasses;


	public static void main(String[] args) {
		CommandLine.run(new GetTestCasesToRunCommand(), args);
	}

	@Override
	public void run() {
		if (debug) {
			Config.DEBUG = true;
		}
		AbstractDependenciesReader reader;
		AffectedClassesResolver<ClassDependencies> affectedClassesResolver = new AffectedClassesResolver<>();
		affectedClassesResolver.setIncludeChildrenClasses(!noChildrenClasses);
		if (readerName.equals("json")) {
			reader = new JsonDependenciesReader(testLibrary, suffix);
		} else {
			throw new RuntimeException(
					"Unknown file reader: \"" + readerName + "\"." + "For now available options are: " + Config.getAvailableExtensionsList() + ".");
		}
		try {
			Map<String, ClassDependencies> testTree = reader.readDependencies();
			if (libraries != null) {
				libraries.forEach(lib -> {
					reader.setPath(lib);
					try {
						affectedClassesResolver.addDependencyTree(reader.readDependencies());
					} catch (FileNotFoundException e) {
						System.out.println("Couldn't read " + lib + ". Continuing without this one.");
					}
				});
				classes = affectedClassesResolver.getAffectedClasses(classes);
			}
			TestCasesResolver testCasesResolver = new TestCasesResolver(testTree);
			List<String> tests = testCasesResolver.getTestCasesToRun(classes);
			System.out.println(String.join("\n", tests));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
