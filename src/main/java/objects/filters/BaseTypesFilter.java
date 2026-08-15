package objects.filters;

import objects.modelica.IClassName;

import java.util.List;

public class BaseTypesFilter implements IFilter<IClassName> {

    private final List<String> baseTypes = List.of(
            "Real",
            "Boolean",
            "String",
            "Integer"
    );

    public BaseTypesFilter() {
    }

    @Override
    public boolean shouldBeUsed(IClassName valueToCheck) {
        return !baseTypes.contains(valueToCheck.getClassName());
    }
}
