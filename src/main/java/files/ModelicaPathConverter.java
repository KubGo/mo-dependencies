package files;

public class ModelicaPathConverter {
	private String libraryName;

	public ModelicaPathConverter() {
		this("");
	}

	public ModelicaPathConverter(String libraryName){
		this.libraryName = libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public ModelicaPathInfo convertToModelicaPath(String systemPath){
		return new ModelicaPathInfo(
				systemPath,
				this.libraryName
		);
	}

}
