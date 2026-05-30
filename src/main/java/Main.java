import commands.*;
import picocli.CommandLine;

@CommandLine.Command(name = "modep", subcommands = {
		CreateDependenciesCommand.class, GetAffectedClassesCommand.class, GetTestCasesToRunCommand.class, GetTestCoverageCommand.class, GetClassesUsageCommand.class})
public class Main implements Runnable {
	public static void main(String[] args) {
		int exitCode = new CommandLine(new Main()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public void run() {

	}
}
