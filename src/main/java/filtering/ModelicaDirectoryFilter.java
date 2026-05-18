package filtering;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Check if directory contains Modelica files stored separately
 * (with package.mo and package.order files)
 */
public class ModelicaDirectoryFilter implements IFilter {
	public ModelicaDirectoryFilter() {

	}

	@Override
	public String getFilterDescription() {
		return "Filtering for directories with package.mo and package.order...";
	}

	/**
	 * @param path - path to directory with files
	 *
	 * @return true if it is directory with Modelica files definitions in separate files,
	 * false otherwise
	 */
	@Override
	public boolean filterName(String path) {
		if (path == null){
			return false;
		}
		List<String> fileNames = Arrays.stream(Objects.requireNonNull(new File(path).listFiles()))
				.map(File::getName).toList();
		if (fileNames.isEmpty()){
			return false;
		}
		return fileNames.stream().filter(this::isPackageFile).toList().size() == 2;
	}

	/**
	 * @param name file name
	 *
	 * @return check if file is package.mo or package.order
	 */
	private boolean isPackageFile(String name){
		return (name.equals("package.mo") || name.equals("package.order"));
	}
}
