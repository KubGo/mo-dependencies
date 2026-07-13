package modelica.packagestructure;

import filtering.IFilter;
import objects.files.ModelicaFile;
import objects.files.ModelicaFolder;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PackageStructureResolver {
	private final String pathToLibrary;
	private final String libraryName;
	private final ModelicaFolder libraryPackage;
	private ModelicaFolder currentPackage;
	private List<IFilter> filters = new ArrayList<>();

	public PackageStructureResolver(String pathToLibrary, String libraryName, List<IFilter> filters) {
		if (pathToLibrary.endsWith(libraryName)) {
			pathToLibrary = Path.of(pathToLibrary).getParent().toString();
		}
		this.pathToLibrary = pathToLibrary;
		this.libraryName = libraryName;
		libraryPackage = new ModelicaFolder(libraryName);
		this.filters = filters;
		resolvePackageStructure(libraryPackage);
	}

	public PackageStructureResolver(String pathToLibrary, String libraryName) {
		this(pathToLibrary, libraryName, List.of());
	}

	public PackageStructureResolver(String pathToLibrary) {
		this(pathToLibrary, Path.of(pathToLibrary).getFileName().toString(), List.of());
	}

	private void resolvePackageStructure(ModelicaFolder modelicaFolder) {
		currentPackage = modelicaFolder;
		List<String> subfolders;
		if (!modelicaFolder.getPath().contains(".")) {
			subfolders = List.of(modelicaFolder.getName());
		}
		else {
			subfolders = List.of(modelicaFolder.getPath().split("\\."));
		}
		Path path = Path.of(pathToLibrary);
		for (var folder : subfolders) {
			path = Path.of(path.toString(), folder);
		}

		File[] files = new File(path.toString()).listFiles();
		if (files == null || files.length < 1) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			boolean filtered = false;
			for (var filter : filters) {
				if (!filter.shouldBeUsed(fileName)) {
					filtered = true;
				}
			}
			if (!filtered) {
				if (file.isDirectory()) {
					ModelicaFolder newPackage = new ModelicaFolder(fileName, modelicaFolder);
					resolvePackageStructure(newPackage);
				} else if (fileName.endsWith(".mo") && !fileName.equals("package.mo")) {
					new ModelicaFile(fileName.split("\\.")[0], modelicaFolder);
				}
			}
		}
		if (modelicaFolder.hasParent()) {
			currentPackage = modelicaFolder.getParent();
		}
	}

	public ModelicaFolder getLibraryPackage() {
		return libraryPackage;
	}

}
