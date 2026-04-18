package filtering;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModelicaPackageFilter implements IFilter{
	public ModelicaPackageFilter(){

	}
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
		return fileNames.stream().filter(this::isPackageFile).toList().size() >= 2;
	}

	private boolean isPackageFile(String name){
		return (name.equals("package.mo") || name.equals("package.order"));
	}
}
