package objects.filters;

import objects.modelica.IClassName;

/**
 * Filters components by from specific library, e.g.:
 * filters components from Modelica Standard Library
 * to not use them.
 */
public class ModelicaLibraryFilter implements IFilter<IClassName> {

    private final String libraryName;

    public ModelicaLibraryFilter(String libraryName) {
        this.libraryName = libraryName;
    }

    @Override
    public boolean shouldBeUsed(IClassName valueToCheck) {
        return !valueToCheck.getClassName().startsWith(libraryName);
    }

}
