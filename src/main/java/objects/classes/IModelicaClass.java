package objects.classes;

import modelica.ModelicaClassType;
import objects.modelica.Component;
import objects.modelica.Declaration;
import objects.modelica.Modification;

import java.util.List;

public interface IModelicaClass {

    ModelicaClassType getClassType();

    IModelicaClass getNext();

    boolean hasNext();

    void reset();

    String getClassPath();

    String getClassName();

    List<Component> getComponents();

    List<Modification> getModifications();

    List<Declaration> getDeclarations();

    void getClassDefinitions(String text);

    ModelicaPackage getParentPackage();

    void setParentPackage(ModelicaPackage parentPackage);

    boolean pathMatches(String path);
}
