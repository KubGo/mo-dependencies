package objects.classes;

import modelica.ModelicaClassType;
import objects.modelica.Component;
import objects.modelica.Modification;

import java.util.List;

public interface IModelicaClass {

	ModelicaClassType getClassType();

	IModelicaClass getNext();

	boolean hasNext();

	void reset();

	String getClassPath();

	String getClassName();

	List<Component> getDeclarations();

	List<Modification> getModifications();

	void getClassDefinitions(String text);
}
