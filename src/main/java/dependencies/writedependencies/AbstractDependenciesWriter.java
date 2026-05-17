package dependencies.writedependencies;

import dependencies.classesinfo.ClassDependencies;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Abstract class for writing dependencies to file
 */
public abstract class AbstractDependenciesWriter {
	protected String extension;
	protected String path;
	protected String suffix;
	protected String libraryName;

	/**
	 * @param dependencies map of {@link ClassDependencies} to write to file
	 * @throws IOException when any error in writing the file happens
	 */
	public abstract void writeDependencies(TreeMap<String, ClassDependencies> dependencies) throws IOException;

	/**
	 * @return file name of saved file
	 */
	public String getFileName() {
		return libraryName + suffix + extension;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
