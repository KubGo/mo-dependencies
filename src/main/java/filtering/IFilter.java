package filtering;

public interface IFilter {
	/**
	 * @param text - string value used for filtering operations
	 *
	 * @return true if it should be used, false otherwise
	 */
	boolean filterName(String text);
}
