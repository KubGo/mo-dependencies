package dependencies.structureinfo;

import files.ModelicaPathConverter;
import files.ModelicaPathInfo;

public abstract class ModelicaFileInfo {
	protected ModelicaPathConverter modelicaPathConverter = new ModelicaPathConverter();
	ModelicaPathInfo modelicaPathInfo;
	protected ModelicaFileInfo parent;
}
