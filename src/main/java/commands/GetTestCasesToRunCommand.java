package commands;

import config.Config;
import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "getTestCases", description = "Get test cases to run for affected classes")
public class GetTestCasesToRunCommand implements Runnable {
	@CommandLine.Option(names = {"--debug", "-d"})
	private boolean debug;
	@CommandLine.Option(names = {"--classes", "-c"}, required = true)
	private List<String> classes;
	@CommandLine.Option(names = {"--test-library", "-l"}, required = true)
	private String testLibrary;
	@CommandLine.Option(names = {"--reader", "-r"}, defaultValue = "json")
	private String readerName;
	@CommandLine.Option(names = {"--suffix", "-s"}, defaultValue = "_dependencies")
	private String suffix;

	public static void main(String[] args) {
		CommandLine.run(new GetTestCasesToRunCommand(), args);
	}

	@Override
	public void run() {
		if (debug) {
			Config.DEBUG = true;
		}
//		TestCasesResolver testCasesResolver = new TestCasesResolver();
	}
}
