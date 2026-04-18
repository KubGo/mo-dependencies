package dependencies.structureinfo;

public class ClassInfo extends ModelicaFileInfo {
	public String path;

	public ClassInfo(String systemPath, PackageInfo parent){
		this.path = systemPath;
		this.parent = parent;
		this.modelicaPathConverter.setLibraryName(parent.modelicaPathInfo.libraryName);
		modelicaPathInfo = modelicaPathConverter.convertToModelicaPath(systemPath);
	}

	@Override
	public String toStringWithIndent(int indent, int limit) {
		if (limit < indent){
			return null;
		}
		return " ".repeat(2 * indent) + modelicaPathInfo.className + "\n";
	}
}
