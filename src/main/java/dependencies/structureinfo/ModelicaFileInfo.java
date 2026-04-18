package dependencies.structureinfo;

import files.ModelicaPathConverter;
import files.ModelicaPathInfo;

public abstract class ModelicaFileInfo {
	protected ModelicaPathConverter modelicaPathConverter = new ModelicaPathConverter();
	ModelicaPathInfo modelicaPathInfo;
	protected ModelicaFileInfo parent;

	public abstract String toStringWithIndent(int indent, int limit);

	public String toStringWithIndent(int indent){
		return toStringWithIndent(indent, 4);
	}

	public String getClassName(){
		return modelicaPathInfo.className;
	}
	public String getModelicaPath(){
		return modelicaPathInfo.path;
	}
	public ModelicaFileInfo getParent(){
		return parent;
	}
}
