package filtering;

public interface IFilter {
	/**
	 * @param text - string value used for filtering operations
	 *
	 * @return true if it should be used, false otherwise
	 */
	boolean filterName(String text);

	/**
	 * @return description of filter. Mostly for debugging of the app
	 * to let the user know what operation is now performed
	 */
	String getFilterDescription();
}
