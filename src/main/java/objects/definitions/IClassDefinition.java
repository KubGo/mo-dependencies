package objects.definitions;

import modelica.ModelicaClassType;

import java.util.List;

public interface IClassDefinition {

	ModelicaClassType getClassType();

	IClassDefinition getNext();

	boolean hasNext();

	void reset();

	String getClassPath();

	String getClassName();

	List<Component> getDeclarations();

	List<Modification> getModifications();
}
