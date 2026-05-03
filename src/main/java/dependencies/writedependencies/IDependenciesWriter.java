package dependencies.writedependencies;

import dependencies.DependencyTree;

public abstract class IDependenciesWriter {
	protected String extension;
	protected String path;
	protected String suffix;
	protected String libraryName;

	public abstract void writeDependencies(DependencyTree dependencies);

	public String getFileName() {
		return libraryName + suffix + extension;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
