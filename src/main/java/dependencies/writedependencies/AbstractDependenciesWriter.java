package dependencies.writedependencies;

import dependencies.DependencyTree;

import java.io.IOException;

public abstract class AbstractDependenciesWriter {
	protected String extension;
	protected String path;
	protected String suffix;
	protected String libraryName;

	public abstract void writeDependencies(DependencyTree dependencies) throws IOException;

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
