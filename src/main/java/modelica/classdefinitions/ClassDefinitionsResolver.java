package modelica.classdefinitions;

import files.ModelicaFileReader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import objects.classes.ModelicaClass;
import objects.classes.ModelicaPackage;
import objects.files.IModelicaFile;
import objects.files.ModelicaFile;
import objects.files.ModelicaFolder;

import java.io.IOException;

@NoArgsConstructor
public class ClassDefinitionsResolver {

    private final ModelicaFileReader modelicaFileReader = new ModelicaFileReader();
    private ModelicaFolder libraryStructure;
    @Getter
    private ModelicaPackage modelicaLibrary;

    public ClassDefinitionsResolver(ModelicaFolder libraryStructure) {
        this.libraryStructure = libraryStructure;
        modelicaLibrary = generateClassDefinitions(libraryStructure);
    }

    public ModelicaPackage generateClassDefinitions(ModelicaFolder libraryStructure) {
        libraryStructure.reset();
        IModelicaFile modelicaFile = libraryStructure.getNext();
        modelicaLibrary = new ModelicaPackage(modelicaFile);
        while (this.libraryStructure.hasNext()) {
            IModelicaFile file = this.libraryStructure.getNext();
            if (file instanceof ModelicaFile) {
                ModelicaClass modelicaClass = new ModelicaClass(file);
                try {
                    String text = modelicaFileReader.readFile(file.getFilePath());
                    modelicaClass.getClassDefinitions(text);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                ModelicaPackage modelicaPackage = new ModelicaPackage(file);
            }
        }
        return modelicaLibrary;
    }
}
