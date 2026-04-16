package dependencies.structureinfo;

public class ClassInfo extends ModelicaFileInfo {

	public ClassInfo(String systemPath, PackageInfo parent){
		this.parent = parent;
		this.modelicaPathConverter.setLibraryName(parent.modelicaPathInfo.libraryName);
		modelicaPathInfo = modelicaPathConverter.convertToModelicaPath(systemPath);
	}
}
