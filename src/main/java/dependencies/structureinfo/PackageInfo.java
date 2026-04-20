package dependencies.structureinfo;


import java.util.ArrayList;
import java.util.List;

public class PackageInfo extends ModelicaFileInfo{
	List<ModelicaFileInfo> children = new ArrayList<>();

	public PackageInfo(String systemPath, PackageInfo parent){
		this.modelicaPathConverter.setLibraryName(parent.modelicaPathInfo.libraryName);
		modelicaPathInfo = modelicaPathConverter.convertToModelicaPath(
				systemPath);
		this.parent = parent;
	}

	@Override
	public PackageInfo getParent() {
		return (parent == null) ? null : (PackageInfo) parent;
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

	public List<ClassInfo> getClassDefinitions(){
		return children.stream().filter(
				x -> x instanceof ClassInfo)
				.map(x -> (ClassInfo) x)
				.toList();
	}

	@Override
	public String toStringWithIndent(int indent, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.repeat(" ", 2 * indent).append(modelicaPathInfo.className).append("\n");
		if (indent + 1 < limit) {
			for (var child : children) {
				sb.append(child.toStringWithIndent(indent + 1, limit));
			}
		}
		return sb.toString();
	}

	@Override
	public ModelicaFileInfo matchPath(String className) {
		for (ModelicaFileInfo file : children) {
			if (file.getClassName().equals(className)) {
				return file;
			}
		}
		return null;
	}

	@Override
	public boolean isFinal() {
		return children.isEmpty();
	}
}
