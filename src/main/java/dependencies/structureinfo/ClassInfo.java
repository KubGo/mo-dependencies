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
	public ModelicaFileInfo getParent() {
		return (parent == null) ? null : (PackageInfo) parent;
	}

	@Override
	public String toStringWithIndent(int indent, int limit) {
		return " ".repeat(2 * indent) + modelicaPathInfo.className + "\n";
	}

	@Override
	public ModelicaFileInfo matchPath(String className) {
		return this;
	}

	@Override
	public boolean isFinal() {
		return true;
	}
}
