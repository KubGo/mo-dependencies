package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassesExtractor extends ModelicaBaseListener{
    List<String> imports = new ArrayList<>();
    Set<String> classes = new HashSet<>();

    @Override
    public void enterType_specifier(ModelicaParser.Type_specifierContext ctx) {
        String className = ctx.getText();
        classes.add(className);
    }

    @Override
    public void enterImport_clause(ModelicaParser.Import_clauseContext ctx) {
        String importClause = ctx.getText();
        imports.add(importClause);
    }

}
