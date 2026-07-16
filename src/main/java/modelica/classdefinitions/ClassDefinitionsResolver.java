package modelica.classdefinitions;

import files.ModelicaFileReader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import objects.classes.ModelicaClass;
import objects.classes.ModelicaPackage;
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
        ModelicaFolder currentFolder = libraryStructure;
        try {
            resolveFolderClassDefinitions(libraryStructure);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return modelicaLibrary;
    }

    private void resolveFolderClassDefinitions(ModelicaFolder modelicaFolder, ModelicaPackage parentPackage) throws IOException {
        ModelicaPackage modelicaPackage = new ModelicaPackage(modelicaFolder);
        if (parentPackage == null) {
            this.modelicaLibrary = modelicaPackage;
        } else {
            modelicaPackage.setParentPackage(parentPackage);
        }
        String text = modelicaFileReader.readFile(modelicaFolder.getFilePath());
        modelicaPackage.getClassDefinitions(text);
        if (modelicaFolder.hasChildren()) {
            for (var modelicaFile : modelicaFolder.getAllFiles()) {
                text = modelicaFileReader.readFile(modelicaFile.getFilePath());
                ModelicaClass modelicaClass = new ModelicaClass(modelicaFile);
                modelicaClass.setParentPackage(modelicaPackage);
                modelicaClass.getClassDefinitions(text);
            }
        }
        for (var folder : modelicaFolder.getAllFolders()) {
            resolveFolderClassDefinitions(folder, modelicaPackage);
        }
    }

    private void resolveFolderClassDefinitions(ModelicaFolder modelicaFolder) throws IOException {
        resolveFolderClassDefinitions(modelicaFolder, (ModelicaPackage) null);
    }
}
