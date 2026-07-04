package objects.definitions;

import modelica.ModelicaClassType;

import java.util.List;

public class DeclarationBuilder {
    private ModelicaClassType type;
    private String declarationName;
    private String declarationClass;
    private List<Modification> modifications = List.of();
    private String description = "";
    private String constrainingClass = null;

    public DeclarationBuilder setType(ModelicaClassType type) {
        this.type = type;
        return this;
    }

    public DeclarationBuilder setDeclarationName(String declarationName) {
        this.declarationName = declarationName;
        return this;
    }

    public DeclarationBuilder setDeclarationClass(String declarationClass) {
        this.declarationClass = declarationClass;
        return this;
    }

    public DeclarationBuilder setModifications(List<Modification> modifications) {
        this.modifications = modifications;
        return this;
    }

    public DeclarationBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public DeclarationBuilder setConstrainingClass(String constrainingClass) {
        this.constrainingClass = constrainingClass;
        return this;
    }

    public Declaration build() {
        return new Declaration(type, declarationName, declarationClass, modifications, description, constrainingClass);
    }
}