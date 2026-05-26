package commands;

import config.Config;
import dependencies.classesinfo.ClassDependencies;
import dependencies.readdependencies.AbstractDependenciesReader;
import dependencies.readdependencies.JsonDependenciesReader;
import functionalities.coverage.TestCoverage;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.util.Map;

@CommandLine.Command(name = "getTestCoverage", description = "Get test coverage of the test library over development " + "library")
public class GetTestCoverageCommand implements Runnable {
	@CommandLine.Option(names = {"--library", "-l"})
	private String library;
	@CommandLine.Option(names = {"--test-library", "-t"}, required = true)
	private String testLibrary;
	@CommandLine.Option(names = {"--debug", "-d"})
	private boolean debug;
	@CommandLine.Option(names = {"--reader", "-r"}, defaultValue = "json")
	private String readerName;
	@CommandLine.Option(names = {"--suffix", "-s"}, defaultValue = "_dependencies")
	private String suffix;

	public static void main(String[] args) {
		CommandLine.run(new GetTestCoverageCommand(), args);
	}

	@Override
	public void run() {
		if (debug) {
			Config.DEBUG = true;
		}
		AbstractDependenciesReader reader;
		if (readerName.equals("json")) {
			reader = new JsonDependenciesReader(testLibrary, suffix);
		}
		else {
			throw new RuntimeException(
					"Unknown file reader: \"" + readerName + "\"." + "For now available options are: " + Config.getAvailableExtensionsList() + ".");
		}

		try {
			reader.setPath(library);
			Map<String, ClassDependencies> libraryTree = reader.readDependencies();
			reader.setPath(testLibrary);
			Map<String, ClassDependencies> testTree = reader.readDependencies();
			TestCoverage testCoverage = new TestCoverage(libraryTree, testTree);
			System.out.println(testCoverage);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
