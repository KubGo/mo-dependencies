package objects.definitions;

import lombok.Getter;
import modelica.ModelicaClassType;

import java.util.List;

public class Declaration {
    @Getter
    ModelicaClassType type;
    @Getter
    String declarationName;
    @Getter
    String declarationClass;
    @Getter
    List<Modification> modifications;
    @Getter
    String description;
    @Getter
    String constrainingClass;

    public Declaration(ModelicaClassType type, String declarationName, String declarationClass, List<Modification> modifications, String description, String constrainingClass) {
        this.type = type;
        this.declarationName = declarationName;
        this.declarationClass = declarationClass;
        this.modifications = modifications;
        this.description = description;
        this.constrainingClass = constrainingClass;
    }
}
