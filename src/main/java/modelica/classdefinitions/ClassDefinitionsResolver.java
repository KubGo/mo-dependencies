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
            String text = "";
            try {
                text = modelicaFileReader.readFile(file.getFilePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (file instanceof ModelicaFile) {
                ModelicaClass modelicaClass = new ModelicaClass(file);
                modelicaClass.getClassDefinitions(text);
            } else {
                ModelicaPackage modelicaPackage = new ModelicaPackage(file);
                modelicaPackage.getClassDefinitions(text);
            }
        }
        return modelicaLibrary;
    }
}
