package files;

/**
 * Modelica path converter from file path
 */
public class ModelicaPathConverter {
	private String libraryName;

	public ModelicaPathConverter() {
		this("");
	}

	/**
	 * @param libraryName name of the library the models belong
	 */
	public ModelicaPathConverter(String libraryName){
		this.libraryName = libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	/**
	 * @param systemPath system path to the model
	 * @return Info regarding the Modelica path
	 * @see ModelicaPathInfo
	 */
	public ModelicaPathInfo convertToModelicaPath(String systemPath){
		return new ModelicaPathInfo(
				systemPath,
				this.libraryName
		);
	}

}
