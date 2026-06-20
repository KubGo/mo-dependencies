package modelica.packagestructure;

import objects.modelica.ModelicaClass;
import objects.modelica.ModelicaPackage;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class PackageStructureResolver {
	private final String pathToLibrary;
	private final String libraryName;
	private final ModelicaPackage libraryPackage;
	private ModelicaPackage currentPackage;

	public PackageStructureResolver(String pathToLibrary, String libraryName) {
		if (pathToLibrary.endsWith(libraryName)) {
			pathToLibrary = Path.of(pathToLibrary).getParent().toString();
		}
		this.pathToLibrary = pathToLibrary;
		this.libraryName = libraryName;
		libraryPackage = new ModelicaPackage(libraryName);
		resolvePackageStructure(libraryPackage);
	}

	public PackageStructureResolver(String pathToLibrary) {
		this(pathToLibrary, Path.of(pathToLibrary).getFileName().toString());
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
		Path path = Path.of(pathToLibrary, subfolders.toArray(new String[0]));

		File[] files = new File(path.toString()).listFiles();
		for (File file : files) {
			String fileName = file.getName();
			if (file.isDirectory()) {
				ModelicaPackage newPackage = new ModelicaPackage(fileName, modelicaPackage);
				resolvePackageStructure(newPackage);
			}
			else if (fileName.endsWith(".mo") && !fileName.equals("package.mo")) {
				ModelicaClass modelicaClass = new ModelicaClass(fileName.split("\\.")[0], modelicaPackage);
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
