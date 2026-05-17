package dependencies.structureinfo;


import java.util.ArrayList;
import java.util.List;

/**
 * Contains the information about the package
 */
public class PackageInfo extends ModelicaFileInfo{
	List<ModelicaFileInfo> children = new ArrayList<>();

	/**
	 * @param systemPath path to the file
	 * @param parent     parent of this class. This is {@link PackageInfo} package that
	 *                   contains this class definition.
	 */
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

	/**
	 * @return classes that are in this package
     */
	public List<ModelicaFileInfo> getChildren(){
		return children;
	}

	/**
	 * @return class definitions in this package without other packages.
     */
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

	/**
	 * @param className class name of this file
	 * @return ModelicaFileInfo that can be found in this package that matches this path.
	 * If the file that equals this path is not found return null.
     */
	@Override
	public ModelicaFileInfo matchPath(String className) {
		for (ModelicaFileInfo file : children) {
			if (file.getClassName().equals(className)) {
				return file;
			}
		}
		return null;
	}

	/**
	 * @return true if this package is empty
     */
	@Override
	public boolean isFinal() {
		return children.isEmpty();
	}
}
