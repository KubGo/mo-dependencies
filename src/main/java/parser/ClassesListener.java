package parser;

import modelica.ClassTypeProvider;
import modelica.ModelicaClassType;
import modelica.ModelicaFileSection;

import java.util.*;

public class ClassesListener extends ModelicaBaseListener{
    public String packageName;
    public ModelicaClassType modelicaClassType;
    public List<String> imports = new ArrayList<>();
    public Set<String> classes = new HashSet<>();
    public Set<String> functions = new HashSet<>();
    public List<String> parentClasses = new ArrayList<>();
    public Map<String, String> constrainingClassesMap = new HashMap<>();
    public Map<String, String> classDefinitionsMap = new HashMap<>();
    private String typeName = "";

    private ModelicaFileSection currentSection = ModelicaFileSection.DECLARATIVE;
    private final Stack<ModelicaFileSection> lastSections = new Stack<>();

    private void updateSection(ModelicaFileSection section){
        lastSections.push(currentSection);
        currentSection = section;
    }

    private void popSection() {
        currentSection = lastSections.pop();
    }

    @Override
    public void enterStored_definition(ModelicaParser.Stored_definitionContext ctx) {
        packageName = ctx.name().getFirst().getText();
        modelicaClassType = ClassTypeProvider.getClassType(
                ctx.class_definition().getFirst().class_prefixes().getText());

    }

    @Override
    public void enterType_specifier(ModelicaParser.Type_specifierContext ctx) {
        String className = ctx.getText();
        classes.add(className);
    }

    @Override
    public void enterImport_clause(ModelicaParser.Import_clauseContext ctx) {
        String importClause = ctx.name().getText();
        imports.add(importClause);
    }

    @Override
    public void enterAnnotation(ModelicaParser.AnnotationContext ctx) {
        updateSection(ModelicaFileSection.ANNOTATION);
    }

    @Override
    public void exitAnnotation(ModelicaParser.AnnotationContext ctx) {
        popSection();
    }

    @Override
    public void enterEquation_section(ModelicaParser.Equation_sectionContext ctx) {
        updateSection(ModelicaFileSection.EQUATION);
    }

    @Override
    public void exitEquation_section(ModelicaParser.Equation_sectionContext ctx) {
        popSection();
    }

    @Override
    public void enterAlgorithm_section(ModelicaParser.Algorithm_sectionContext ctx) {
        updateSection(ModelicaFileSection.ALGORITHM);
    }

    @Override
    public void exitAlgorithm_section(ModelicaParser.Algorithm_sectionContext ctx) {
        popSection();
    }

    @Override
    public void enterPrimary(ModelicaParser.PrimaryContext ctx) {
        if (currentSection != ModelicaFileSection.ANNOTATION) {
            var name = ctx.name();
            var call = ctx.function_call_args();
            if (name != null && call != null) {
                functions.add(name.getText());
            }
        }
    }

    @Override
    public void enterExtends_clause(ModelicaParser.Extends_clauseContext ctx) {
        String extendingClass = ctx.name().getText();
        parentClasses.add(extendingClass);
    }

    @Override
    public void enterElement(ModelicaParser.ElementContext ctx) {
        if (ctx.constraining_clause() != null) {
            String constrainingClause;
            if (ctx.component_clause() == null){
                constrainingClause = "choicesAllMatching";
			} else {
                constrainingClause = ctx.component_clause().type_specifier().name().getText();
            }
            constrainingClassesMap.put(
                    ctx.constraining_clause().name().getText(),
                    constrainingClause
            );
        }
    }

    @Override
    public void enterClass_definition(ModelicaParser.Class_definitionContext ctx) {
        if (ctx.class_specifier().short_class_specifier() != null) {
            var shortClassSpecifier = ctx.class_specifier().short_class_specifier();
            if (shortClassSpecifier.name() == null){
                typeName = shortClassSpecifier.getText().split("=")[0].trim();
                return;
			}
            String className = shortClassSpecifier.name().getText();
            String name = shortClassSpecifier.getText().split("=")[0].trim();
            classDefinitionsMap.put(
                    name,
                    className
            );
        }
    }


    @Override
    public void enterEnumeration_literal(ModelicaParser.Enumeration_literalContext ctx) {
        classDefinitionsMap.put(
                ctx.getText().split("\"")[0].trim(),
                typeName);
    }
}
