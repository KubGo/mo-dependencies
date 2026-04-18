package filtering;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DirectoryNameFilter implements IFilter {
	private List<Pattern> patterns = new ArrayList<>();

	public DirectoryNameFilter(){

	}

	@Override
	public boolean filterName(String text) {
		if (patterns.isEmpty()){
			return true;
		}
		for (Pattern pattern : patterns) {
			if (pattern.matcher(text).find()) {
				return false;
			}
		}
		return true;
	}
}
