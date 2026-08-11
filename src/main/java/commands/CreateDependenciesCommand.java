package commands;

import config.Config;
import facade.LibraryResolutionFacade;
import io.write.JsonPackageWriter;
import objects.classes.ModelicaPackage;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.List;

// TODO("Change to new implementation")
// TODO("Delete old implementation")
@CommandLine.Command(name = "createDependencies", description = "Creates dependencies for library"

)
public class CreateDependenciesCommand implements Runnable {
    @CommandLine.Parameters(description = "Path to library to resolve or to folder with libraries to resolve in recursive mode.")
    private String libraryPath;
    @CommandLine.Option(names = {"--recursive", "-r"}, description = "Sets recursive search for all libraries below specified path.")
    private boolean recursive;
    @CommandLine.Option(names = {"--no-extends",}, description = "Do not resolve extending classes.")
    private boolean noExtendingClasses;
    @CommandLine.Option(names = {"--relative-paths"}, description = "Leave relative paths. Do not change paths to absolute paths.")
    private boolean relativePaths;
    @CommandLine.Option(names = {"--debug", "-d"})
    private boolean debug;
    @CommandLine.Option(names = {"--verbose", "-v"})
    private boolean verbose;
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
        Config.DEBUG = debug;
        if (debug) {
            Config.VERBOSE = verbose;
        } else {
            System.out.println("Set also flag -d for debug to get verbose output.");
        }
        if (Config.DEBUG) {
            System.out.println("Creating dependencies...");
        }
        if (librariesToFilter == null) {
            librariesToFilter = List.of();
        }
        LibraryResolutionFacade facade = new LibraryResolutionFacade(
                !relativePaths,
                !noExtendingClasses,
                recursive
        );
        List<ModelicaPackage> libraries = facade.resolveLibraries(libraryPath);
        JsonPackageWriter writer = new JsonPackageWriter();
        Path path = Path.of(libraryPath).toAbsolutePath();
        if (path.toFile().isFile()) {
            path = path.getParent();
        }
        for (var library : libraries) {
            writer.save(library, path);
        }


        // TODO("Allow to filter with new implementation")
//        List<ModelicaLibraryFilter> filters = librariesToFilter.stream().map(ModelicaLibraryFilter::new).toList();
//        DependencyTreeResolver dependencyResolver = new DependencyTreeResolver(filters);
//        if (additionalLibraries != null) {
//            additionalLibraries.forEach(lib -> {
//                DependencyTreeResolver additionalResolver = new DependencyTreeResolver(filters);
//                additionalResolver.generateLibraryDependencies(lib);
//                dependencyResolver.addLibraryDependencies(additionalResolver.getSimplifiedClassDependencies());
//            });
//        }
//        dependencyResolver.generateLibraryDependencies(libraryPath);
//        List<AbstractDependenciesWriter> dependenciesWriters = new ArrayList<>();
//        for (String writer : writers) {
//            if (writer.equals("json")) {
//                dependenciesWriters.add(new JsonDependenciesWriter());
//            } else {
//                System.out.println(
//                        "Unknown writer \"" + writer + "\", " + "for now available options are: " + Config.getAvailableExtensionsList() + ".");
//            }
//        }
//        dependenciesWriters.forEach(dependencyResolver::saveDependencies);
//        if (Config.DEBUG) System.out.println("Dependencies created.");
    }
}
