package filtering;

import java.util.Objects;

/**
 * Filters absolute paths from specified parser.Modelica library name
 */
public class ModelicaLibraryFilter implements IFilter {
	private final String libraryName;

	/**
	 * @param libraryName name of the library that should be filtered
	 */
	public ModelicaLibraryFilter(String libraryName) {
		this.libraryName = libraryName;
	}

	/**
	 * @param classPath - absolute path to parser.Modelica model
	 *
	 * @return true if class path should be used, false otherwise, e.g.:
	 * {@code ModelicaLibraryFilter filter = new ModelicaLibraryFilter("parser.Modelica");
	 * filter.filterName("parser.Modelica.Units.SI.Length") // returns false}
	 */
	@Override
	public boolean shouldBeUsed(String classPath) {
		String[] paths = classPath.split("\\.");
		return !Objects.equals(paths[0], libraryName);
	}

	@Override
	public String getFilterDescription() {
		return "Filtering classes that aren't from " + libraryName + "...";
	}
}
