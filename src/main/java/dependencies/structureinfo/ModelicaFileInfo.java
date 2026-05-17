package dependencies.structureinfo;

import files.ModelicaPathConverter;
import files.ModelicaPathInfo;

/**
 * Abstract class for holding modelica file information
 */
public abstract class ModelicaFileInfo {
	protected ModelicaPathConverter modelicaPathConverter = new ModelicaPathConverter();
	ModelicaPathInfo modelicaPathInfo;
	protected ModelicaFileInfo parent;

	/**
	 * @param indent indent value
	 * @param limit  max value of indent to use
	 * @return indented string value
	 */
	public abstract String toStringWithIndent(int indent, int limit);

	/**
	 * @param indent indent value
	 * @return indented string value
	 */
	public String toStringWithIndent(int indent){
		return toStringWithIndent(indent, 4);
	}

	public String getClassName(){
		return modelicaPathInfo.className;
	}
	public String getModelicaPath(){
		return modelicaPathInfo.path;
	}

	/**
	 * @return parent of this or null
	 */
	public abstract ModelicaFileInfo getParent();

	/**
	 * @param className class name of this file
	 * @return ModelicaFileInfo that matches this path
	 */
	public abstract ModelicaFileInfo matchPath(String className);

	/**
	 * @return true if this file doesn't contain any children
	 */
	public abstract boolean isFinal();
}
