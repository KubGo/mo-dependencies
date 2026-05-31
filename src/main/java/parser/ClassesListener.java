package parser;

import modelica.ClassTypeProvider;
import modelica.ModelicaClassType;
import modelica.ModelicaFileSection;

import java.util.*;

/**
 * Parser listener for getting classes, imports, functions and other Modelica
 * classes used inside the class.
 */
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
    private final Set<String> classModifications = new HashSet<>();
    private final Map<String, String> componentDeclarationsMap = new HashMap<>();
    private boolean classModification = false;
    private boolean componentDeclaration = false;
    private String currentComponentModelicaPath = "";

    public void resolveInternalClassModifications() {
        for (String classModification : classModifications) {
            classes.add(getComponentFromName(classModification));
        }
    }

    private String getComponentFromName(String name) {
        Stack<String> stack = new Stack<>();
        Arrays.stream(name.split("\\.")).forEach(stack::push);
        while (!stack.isEmpty()) {
            String path = String.join(".", stack);
            if (componentDeclarationsMap.containsKey(path)) {
                return componentDeclarationsMap.get(path);
            }
            stack.pop();
        }
        return name;
    }

    public void resolveClassDefinitions() {
        classDefinitionsMap.forEach((name, path) -> {
            classes.add(path);
            if (!path.equals(name)) {
                classes.remove(name);
            }
        });
    }

    public Map<String, String> getComponentDeclarationsMap() {
        return componentDeclarationsMap;
    }

    public List<String> getModifiedClasses() {
        return classModifications.stream().toList();
    }

    /**
     * Current section to retrieve classes, mostly used to allow
     * correct collection of functions. (Annotations get classified as functions)
     */
    private ModelicaFileSection currentSection = ModelicaFileSection.DECLARATIVE;
    private final Stack<ModelicaFileSection> lastSections = new Stack<>();

    /**
     * @param section - encountered section
     */
    private void updateSection(ModelicaFileSection section){
        lastSections.push(currentSection);
        currentSection = section;
    }

    /**
     * Go back to previous section
	 */
    private void popSection() {
        currentSection = lastSections.pop();
    }

    /**
     * Retrieves the package name and class type
     * @param ctx the parse tree
	 */
    @Override
    public void enterStored_definition(ModelicaParser.Stored_definitionContext ctx) {
        packageName = ctx.name().getFirst().getText();
        modelicaClassType = ClassTypeProvider.getClassType(
                ctx.class_definition().getFirst().class_prefixes().getText());

    }

    /**
     * Retrieves class names
     * @param ctx the parse tree
	 */
    @Override
    public void enterType_specifier(ModelicaParser.Type_specifierContext ctx) {
        String className = ctx.getText();
        classes.add(className);
    }

    /**
     * Retrieves imported classes
     * @param ctx the parse tree
	 */
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

    /**
     * Retrieve function calls
     * @param ctx the parse tree
	 */
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
    public void enterComponent_clause(ModelicaParser.Component_clauseContext ctx) {
        if (currentSection != ModelicaFileSection.ANNOTATION) {
            componentDeclaration = true;
            currentComponentModelicaPath = ctx.type_specifier().name().getText();
        }
    }


    @Override
    public void exitComponent_clause(ModelicaParser.Component_clauseContext ctx) {
        componentDeclaration = false;
    }

    @Override
    public void enterDeclaration(ModelicaParser.DeclarationContext ctx) {
        if (componentDeclaration) {
            String componentName = ctx.IDENT().toString();
            componentDeclarationsMap.putIfAbsent(componentName, currentComponentModelicaPath);
        }

    }

    /**
     * Retrieve extending classes
     * @param ctx the parse tree
	 */
    @Override
    public void enterExtends_clause(ModelicaParser.Extends_clauseContext ctx) {
        String extendingClass = ctx.name().getText();
        parentClasses.add(extendingClass);
    }

    /**
     * Get replaceable classes definitions in map
     * @param ctx the parse tree
	 */
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


    /**
     * Get class definitions inside of the class
     * @param ctx the parse tree
	 */
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
            if (ctx.class_prefixes().getText().equalsIgnoreCase("type")) {
                className = name;
            }
            classDefinitionsMap.put(
                    name,
                    className
            );
        }
    }

    @Override
    public void enterComponent_reference(ModelicaParser.Component_referenceContext ctx) {
        if (classModification & currentSection != ModelicaFileSection.ANNOTATION) {
            classModifications.add(ctx.getText());
        }
    }

    /**
     * Class redecorations inside the class
     * @param ctx the parse tree
	 */
    @Override
    public void enterEnumeration_literal(ModelicaParser.Enumeration_literalContext ctx) {
        classDefinitionsMap.put(
                ctx.getText().split("\"")[0].trim(),
                typeName);
    }

    @Override
    public void enterClass_modification(ModelicaParser.Class_modificationContext ctx) {
        classModification = true;
    }

    @Override
    public void exitClass_modification(ModelicaParser.Class_modificationContext ctx) {
        classModification = false;
    }

    @Override
    public void enterElement_redeclaration(ModelicaParser.Element_redeclarationContext ctx) {
        if (!classModification) {
            return;
        }
        if (ctx.component_clause1() != null) {
            classModifications.add(ctx.component_clause1().type_specifier().name().getText());
        }

        if (ctx.isEmpty())
            return;
        if (ctx.short_class_definition() == null) {
            return;
        }
        if (ctx.short_class_definition().short_class_specifier() == null) {
            return;
        }
        var name = ctx.short_class_definition().short_class_specifier().name();
        if (!name.isEmpty()) {
            classModifications.add(name.getText());
        }

    }
}
