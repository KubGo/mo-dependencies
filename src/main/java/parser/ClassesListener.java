package parser;

import modelica.ClassTypeProvider;
import modelica.ModelicaClassType;
import modelica.ModelicaFileSection;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

/**
 * Parser listener for getting classes, imports, functions and other parser.Modelica
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
    private final String typeName = "";
    private final Set<String> modifiedComponents = new HashSet<>();
    private final Map<String, String> componentDeclarationsMap = new HashMap<>();
    private boolean classModification = false;
    private boolean componentDeclaration = false;
    private final Stack<ModelicaFileSection> lastSections = new Stack<>();
    private final Stack<String> currentComponentReferences = new Stack<>();
    private String currentComponentModelicaPath = "";
    private boolean extendsClause = false;
    private boolean constrainingClassDefinition = false;
    private String lastModifiedComponent = "";
    /**
     * Current section to retrieve classes, mostly used to allow
     * correct collection of functions. (Annotations get classified as functions)
     */
    private ModelicaFileSection currentSection = ModelicaFileSection.DECLARATIVE;

    @Override
    public void enterComponent_declaration(Modelica.Component_declarationContext ctx) {
        currentComponentReferences.add(ctx.declaration().IDENT().getText());
    }

    @Override
    public void exitComponent_declaration(Modelica.Component_declarationContext ctx) {
        currentComponentReferences.pop();
    }

    public void resolveInternalClassModifications() {
        for (String modifiedComponent : modifiedComponents) {
            String component = getComponentFromName(modifiedComponent);
            classes.add(component);
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
            if (classes.contains(name)) {
                classes.remove(name);
                classes.add(path);
            }
            if (functions.contains(name)) {
                functions.remove(name);
                functions.add(path);
            }
            componentDeclarationsMap.keySet().forEach(key -> {
                componentDeclarationsMap.replace(key, name, path);
            });
        });
    }

    public Map<String, String> getComponentDeclarationsMap() {
        return componentDeclarationsMap;
    }

    public List<String> getModifiedClasses() {
        return modifiedComponents.stream().toList();
    }

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
    public void enterStored_definition(Modelica.Stored_definitionContext ctx) {
        packageName = ctx.name().getText();
        modelicaClassType = ClassTypeProvider.getClassType(
                ctx.class_definition().getFirst().class_prefixes().getText());
    }

    /**
     * Retrieves class names
     * @param ctx the parse tree
	 */
    @Override
    public void enterType_specifier(Modelica.Type_specifierContext ctx) {
        if (!extendsClause && !constrainingClassDefinition) {
            String className = ctx.getText();
            classes.add(className);
        }
    }

    /**
     * Retrieves imported classes
     * @param ctx the parse tree
	 */
    @Override
    public void enterImport_clause(Modelica.Import_clauseContext ctx) {
        String importClause = ctx.name().getText();
        imports.add(importClause);
    }

    @Override
    public void enterClass_annotation(Modelica.Class_annotationContext ctx) {
        updateSection(ModelicaFileSection.ANNOTATION);
    }

    @Override
    public void exitClass_annotation(Modelica.Class_annotationContext ctx) {
        popSection();
    }

    @Override
    public void enterAnnotation(Modelica.AnnotationContext ctx) {
        updateSection(ModelicaFileSection.ANNOTATION);
    }

    @Override
    public void exitAnnotation(Modelica.AnnotationContext ctx) {
        popSection();
    }

    @Override
    public void enterEquation_section(Modelica.Equation_sectionContext ctx) {
        updateSection(ModelicaFileSection.EQUATION);
    }

    @Override
    public void exitEquation_section(Modelica.Equation_sectionContext ctx) {
        popSection();
    }

    @Override
    public void enterAlgorithm_section(Modelica.Algorithm_sectionContext ctx) {
        updateSection(ModelicaFileSection.ALGORITHM);
    }

    @Override
    public void exitAlgorithm_section(Modelica.Algorithm_sectionContext ctx) {
        popSection();
    }

    /**
     * Retrieve function calls
     * @param ctx the parse tree
	 */
    @Override
    public void enterPrimary(Modelica.PrimaryContext ctx) {
        if (currentSection != ModelicaFileSection.ANNOTATION) {
            var name = ctx.component_reference();
            var call = ctx.function_call_args();
            if (name != null && call != null) {
                functions.add(name.getText());
            }
        }
    }

    @Override
    public void enterComponent_clause(Modelica.Component_clauseContext ctx) {
        if (currentSection != ModelicaFileSection.ANNOTATION && !extendsClause) {
            componentDeclaration = true;
            currentComponentModelicaPath = String.join(
                    ".", ctx.type_specifier()
                            .IDENT()
                            .stream()
                            .map(ParseTree::getText)
                            .toList());
        }
    }


    @Override
    public void exitComponent_clause(Modelica.Component_clauseContext ctx) {
        componentDeclaration = false;
    }

    @Override
    public void enterDeclaration(Modelica.DeclarationContext ctx) {
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
    public void enterExtends_clause(Modelica.Extends_clauseContext ctx) {
        extendsClause = true;
        String extendingClass = ctx.type_specifier().getText();
        parentClasses.add(extendingClass);
    }

    @Override
    public void exitExtends_clause(Modelica.Extends_clauseContext ctx) {
        extendsClause = false;
    }

    /**
     * Get replaceable classes definitions in map
     * @param ctx the parse tree
	 */
    @Override
    public void enterDeclaration_clause(Modelica.Declaration_clauseContext ctx) {
        if (ctx.component_prefix() != null && !extendsClause) {
            List<String> prefixes = ctx.component_prefix().stream().map(RuleContext::getText).toList();
            if (prefixes.contains("replaceable") || prefixes.contains("redeclare")) {
                String constrainingClause;
                String restrictedClass;
                if (ctx.constraining_clause() != null) {
                    constrainingClassDefinition = true;
                    constrainingClause = ctx.constraining_clause().type_specifier().getText();
                }
                else {
                    constrainingClause = "choicesAllMatching";
                }
                if (ctx.component_clause() != null) {
                    restrictedClass = ctx.component_clause().type_specifier().getText();
                    constrainingClassesMap.put(constrainingClause, restrictedClass);
                    classes.add(restrictedClass);

                }
                else {
                    restrictedClass = ctx.class_definition()
                            .class_specifier()
                            .short_class_specifier()
                            .IDENT()
                            .getText();
                    constrainingClassesMap.put(constrainingClause, restrictedClass);
                }
            }
        }
    }

    @Override
    public void exitDeclaration_clause(Modelica.Declaration_clauseContext ctx) {
        constrainingClassDefinition = false;
    }


    @Override
    public void enterElement_redeclaration(Modelica.Element_redeclarationContext ctx) {
        if (currentSection == ModelicaFileSection.ANNOTATION) {
            return;
        }
        if (!classModification) {
            return;
        }
        String componentName;
        String type;
        if (ctx.short_definition() != null) {
            componentName = ctx.short_definition().short_class_specifier().IDENT().getText();
            type = ctx.short_definition().short_class_specifier().type_specifier().getText();

        }
        else if (ctx.short_component_clause() != null) {
            componentName = ctx.short_component_clause().short_component_declaration().declaration().IDENT().getText();
            type = ctx.short_component_clause().type_specifier().getText();

        }
        else {
            return;
        }
        String componentPath = getCurrentComponentReferencePath(componentName);
        modifiedComponents.add(componentPath);
        componentDeclarationsMap.put(componentPath, type);
    }

    private String getCurrentComponentReferencePath(String componentName) {
        return String.join(".", currentComponentReferences) + "." + componentName;
    }

    /**
     * Get class definitions inside the class
     * @param ctx the parse tree
	 */
    @Override
    public void enterClass_definition(Modelica.Class_definitionContext ctx) {
        if (ctx.class_specifier().short_class_specifier() != null) {
            var shortClassSpecifier = ctx.class_specifier().short_class_specifier();
            if (ctx.class_prefixes().getText().equals("type")) {
                return;
            }

            String classType = shortClassSpecifier.type_specifier().getText();
            String name = shortClassSpecifier.IDENT().toString();
            classDefinitionsMap.put(
                    name, classType
            );
        }
    }

    @Override
    public void enterComponent_reference(Modelica.Component_referenceContext ctx) {
        if (classModification & currentSection != ModelicaFileSection.ANNOTATION) {
            String componentPath = getCurrentComponentReferencePath(lastModifiedComponent);
            modifiedComponents.add(componentPath);
            componentDeclarationsMap.put(componentPath, ctx.getText());
        }
    }

    @Override
    public void enterElement_modification(Modelica.Element_modificationContext ctx) {
        lastModifiedComponent = ctx.name().IDENT().getFirst().getText();
    }

    /**
     * Class redecorations inside the class
     * @param ctx the parse tree
	 */
    @Override
    public void enterEnumeration_literal(Modelica.Enumeration_literalContext ctx) {
        classDefinitionsMap.put(
                ctx.IDENT().getText(),
                typeName);
    }

    @Override
    public void enterClass_or_inheritance_modification(Modelica.Class_or_inheritance_modificationContext ctx) {
        classModification = true;
    }

    @Override
    public void exitClass_or_inheritance_modification(Modelica.Class_or_inheritance_modificationContext ctx) {
        classModification = false;
    }

    @Override
    public void enterClass_modification(Modelica.Class_modificationContext ctx) {
        classModification = true;
    }

    @Override
    public void exitClass_modification(Modelica.Class_modificationContext ctx) {
        classModification = false;
    }

    @Override
    public void enterElement_modification_or_replaceable(Modelica.Element_modification_or_replaceableContext ctx) {
        classModification = true;
    }

    @Override
    public void exitElement_modification_or_replaceable(Modelica.Element_modification_or_replaceableContext ctx) {
        classModification = false;
    }
}
