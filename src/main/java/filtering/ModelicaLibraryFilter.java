package filtering;

import java.util.Objects;

public class ModelicaLibraryFilter implements IFilter {
	private final String libraryName;

	public ModelicaLibraryFilter(String libraryName) {
		this.libraryName = libraryName;
	}

	@Override
	public boolean filterName(String classPath) {
		String[] paths = classPath.split("\\.");
		return !Objects.equals(paths[0], libraryName);
	}
}
