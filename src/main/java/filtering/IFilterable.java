package filtering;

import java.util.List;

/**
 * Interface that allows to use IFilter interfaces list to filter the content
 */
public interface IFilterable {

	/**
	 * @param filters - filters that extends the {@link IFilter} interface
	 */
	void filter(List<IFilter> filters);
}
