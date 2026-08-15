package objects.classes;

import lombok.Setter;
import modelica.ModelicaClassType;
import modelica.PathMatcher;
import modelica.pathresolvers.RelativePathResolver;
import objects.files.IModelicaFile;
import objects.files.ModelicaFolder;
import objects.filters.IFilter;
import objects.modelica.*;
import parser.DefinitionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static parser.ParseText.parseText;

public class ModelicaClass implements IModelicaClass {
    private final String className;
    private final String classPath;
    ArrayList<Component> components = new ArrayList<>();
    ArrayList<Modification> modifications = new ArrayList<>();
    ArrayList<Declaration> declarations = new ArrayList<>();
    ArrayList<Redeclaration> redeclarations = new ArrayList<>();
    List<String> extendingClasses = new ArrayList<>();
    @Setter
    ModelicaClassType classType;
    transient private boolean resolved = false;
    transient private boolean exportsResolved = false;
    transient private ModelicaPackage parentPackage = null;

    public ModelicaClass(IModelicaFile modelicaClass) {
        className = modelicaClass.getName();
        classPath = modelicaClass.getPath();
    }

    public void addDeclaration(Component component) {
        components.add(component);
    }

    public void addModification(Modification modification) {
        modifications.add(modification);
    }

    @Override
    public List<Modification> getModifications() {
        return resolveDeclarations(modifications);
    }

    @Override
    public String getClassPath() {
        return classPath;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public List<Component> getComponents() {
        return resolveDeclarations(components);
    }

    @Override
    public List<Declaration> getDeclarations() {
        return declarations;
    }

    @Override
    public ModelicaClassType getClassType() {
        return classType;
    }

    @Override
    public IModelicaClass getNext() {
        resolved = true;
        return this;
    }

    @Override
    public boolean hasNext() {
        return !resolved;
    }

    @Override
    public void resolveExtendingClasses(ModelicaPackage modelicaPackage) {
        if (!exportsResolved) {
            for (String extendingClass : extendingClasses) {
                try {
                    IModelicaClass modelicaClass = modelicaPackage.getByName(extendingClass);
                    if (!modelicaClass.exportsResolved()) {
                        modelicaClass.resolveExtendingClasses(modelicaPackage);
                    }
                    components.addAll(resolveRedeclaration(modelicaClass.getComponents()));
                } catch (RuntimeException ignored) {
                }
            }
            exportsResolved = true;
        }
    }

    private List<Component> resolveRedeclaration(List<Component> parentComponents) {
        return parentComponents.stream()
                .map(it -> it.redeclare(redeclarations))
                .toList();
    }

    @Override
    public void setExportsResolved(boolean resolved) {
        exportsResolved = resolved;
    }

    @Override
    public boolean exportsResolved() {
        return exportsResolved;
    }

    @Override
    public void reset() {
        resolved = false;
    }

    @Override
    public void setParents(ModelicaPackage parentPackage) {
        this.parentPackage = parentPackage;
    }

    public void getClassDefinitions(String text) {
        DefinitionsListener listener = parseText(text);
        components = listener.getComponents();
        modifications = listener.getModifications();
        declarations = listener.getDeclarations();
        redeclarations = listener.getRedeclarations();
        extendingClasses.addAll(listener.getExtendingClasses());
        resolveImports(listener.getImportedClasses());
    }

    private <T extends IDeclarationsResolver> List<T> resolveDeclarations(List<T> values) {
        return values.stream().map(
                it -> it.resolveDeclaration(declarations)).map(it -> (T) it).toList();
    }

    @Override
    public ModelicaPackage getParentPackage() {
        return this.parentPackage;
    }

    @Override
    public void setParentPackage(ModelicaPackage parentPackage) {
        this.parentPackage = parentPackage;
        parentPackage.addChild(this);
    }

    @Override
    public boolean pathMatches(String path) {
        return PathMatcher.isSubPath(this.classPath, path);
    }

    @Override
    public void resolveRelativePaths(ModelicaFolder modelicaFolder) {
        components.forEach(it -> it.resolveRelativePath(modelicaFolder));
        modifications.forEach(it -> it.resolveRelativePath(modelicaFolder));
        declarations.forEach(it -> it.resolveRelativePath(modelicaFolder));
        resolveExports(modelicaFolder);
    }

    @Override
    public void filterByClassName(IFilter<IClassName> filter) {
        components = components.stream().filter(
                filter::shouldBeUsed
        ).collect(Collectors.toCollection(ArrayList::new));
        declarations = declarations.stream().filter(
                filter::shouldBeUsed
        ).collect(Collectors.toCollection(ArrayList::new));
        modifications = modifications.stream().filter(
                filter::shouldBeUsed
        ).collect(Collectors.toCollection(ArrayList::new));
        redeclarations = redeclarations.stream().filter(
                filter::shouldBeUsed
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    private void resolveImports(List<String> imports) {
        // TODO("Resolve imports based on files path to prevent wrong class declarations")
        for (String importedPath : imports) {
            components.forEach(it -> it.resolveImport(importedPath));
            modifications.forEach(it -> it.resolveImport(importedPath));
            declarations.forEach(it -> it.resolveImport(importedPath));
        }
    }

    private void resolveExports(ModelicaFolder modelicaFolder) {
        RelativePathResolver relativePathResolver = new RelativePathResolver(modelicaFolder);
        extendingClasses = extendingClasses.stream().map(relativePathResolver::resolvePath).toList();
    }
}
