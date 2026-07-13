package modelica.packagestructure;

import filtering.IFilter;
import objects.modelica.ModelicaFile;
import objects.modelica.ModelicaPackage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PackageStructureResolver {
	private final String pathToLibrary;
	private final String libraryName;
	private final ModelicaPackage libraryPackage;
	private ModelicaPackage currentPackage;
	private List<IFilter> filters = new ArrayList<>();

	public PackageStructureResolver(String pathToLibrary, String libraryName, List<IFilter> filters) {
		if (pathToLibrary.endsWith(libraryName)) {
			pathToLibrary = Path.of(pathToLibrary).getParent().toString();
		}
		this.pathToLibrary = pathToLibrary;
		this.libraryName = libraryName;
		libraryPackage = new ModelicaPackage(libraryName);
		this.filters = filters;
		resolvePackageStructure(libraryPackage);
	}

	public PackageStructureResolver(String pathToLibrary, String libraryName) {
		this(pathToLibrary, libraryName, List.of());
	}

	public PackageStructureResolver(String pathToLibrary) {
		this(pathToLibrary, Path.of(pathToLibrary).getFileName().toString(), List.of());
	}

	private void resolvePackageStructure(ModelicaPackage modelicaPackage) {
		currentPackage = modelicaPackage;
		List<String> subfolders;
		if (!modelicaPackage.getPath().contains(".")) {
			subfolders = List.of(modelicaPackage.getName());
		}
		else {
			subfolders = List.of(modelicaPackage.getPath().split("\\."));
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
					ModelicaPackage newPackage = new ModelicaPackage(fileName, modelicaPackage);
					resolvePackageStructure(newPackage);
				} else if (fileName.endsWith(".mo") && !fileName.equals("package.mo")) {
					new ModelicaFile(fileName.split("\\.")[0], modelicaPackage);
				}
			}
		}
		if (modelicaPackage.hasParent()) {
			currentPackage = modelicaPackage.getParent();
		}
	}

	public ModelicaPackage getLibraryPackage() {
		return libraryPackage;
	}

}
