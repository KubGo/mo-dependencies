package dependencies.structureinfo;


import java.util.ArrayList;
import java.util.List;

public class PackageInfo extends ModelicaFileInfo{
	List<ModelicaFileInfo> children = new ArrayList<>();

	public PackageInfo(String systemPath, PackageInfo parent){
		modelicaPathInfo = modelicaPathConverter.convertToModelicaPath(
				systemPath);
		this.parent = parent;
	}

	public PackageInfo(String systemPath, String libraryName){
		this.modelicaPathConverter.setLibraryName(libraryName);
		modelicaPathInfo = modelicaPathConverter.convertToModelicaPath(systemPath);
		parent = null;
	}

	public void addChild(ModelicaFileInfo fileInfo){
		children.add(fileInfo);
	}

	public List<ModelicaFileInfo> getChildren(){
		return children;
	}
}
