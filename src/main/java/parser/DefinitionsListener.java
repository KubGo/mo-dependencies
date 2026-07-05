package parser;

import lombok.Getter;
import modelica.ComponentPrefix;
import modelica.ModelicaClassType;
import modelica.ModelicaFileSection;
import modelica.ModelicaVariability;
import objects.definitions.*;

import java.util.ArrayList;
import java.util.Stack;

import static modelica.ClassTypeProvider.resolveModelicaClassType;

public class DefinitionsListener extends ModelicaBaseListener {

	private final ComponentBuilder componentBuilder = new ComponentBuilder();
	private final DeclarationBuilder declarationBuilder = new DeclarationBuilder();
	@Getter
	public ModelicaClassType modelicaClassType;
	@Getter
	ArrayList<Component> components = new ArrayList<>();
	@Getter
	ArrayList<Declaration> declarations = new ArrayList<>();
	@Getter
	ArrayList<Modification> modifications = new ArrayList<>();
	@Getter
	ArrayList<String> extendingClasses = new ArrayList<>();
	@Getter
	ArrayList<String> importedClasses = new ArrayList<>();
	@Getter
	String name;
	@Getter
	String packageName;
	private String currentClassName;

	private final Stack<ModelicaFileSection> sectionsStack = new Stack<>();
	private final Stack<String> componentNames = new Stack<>();

	/**
	 * Retrieves the package name and class type
	 *
	 * @param ctx the parse tree
	 */
	@Override
	public void enterStored_definition(Modelica.Stored_definitionContext ctx) {
		sectionsStack.add(ModelicaFileSection.DECLARATIVE);
		packageName = ctx.name().getText();
		modelicaClassType = resolveModelicaClassType(
				ctx.class_definition().getFirst().class_prefixes().getText());
		name = ctx.class_definition().getFirst().class_specifier().long_class_specifier().IDENT().getText();
	}

	@Override
	public void enterComponent_clause(Modelica.Component_clauseContext ctx) {
		currentClassName = ctx.type_specifier().getText();
		componentBuilder.setClassName(currentClassName);
	}

	@Override
	public void enterComponent_prefix(Modelica.Component_prefixContext ctx) {
		setComponentPrefix(ctx.getText());
	}

	private void setComponentPrefix(String text) {
		switch (text.toUpperCase()) {
			case ("REPLACEABLE") -> {
				componentBuilder.setComponentPrefix(ComponentPrefix.REPLACEABLE);
			}
			case ("REDECLARE REPLACEABLE") -> {
				componentBuilder.setComponentPrefix(ComponentPrefix.REDECLARE_REPLACEABLE);
			}
			case ("REDECLARE") -> {
				componentBuilder.setComponentPrefix(ComponentPrefix.REDECLARE);
			}
			case ("INNER") -> {
				componentBuilder.setComponentPrefix(ComponentPrefix.INNER);
			}
			case ("OUTER") -> {
				componentBuilder.setComponentPrefix(ComponentPrefix.OUTER);
			}
			case ("FINAL") -> {
				componentBuilder.setComponentPrefix(ComponentPrefix.FINAL);
			}
			default -> {
				componentBuilder.setComponentPrefix(ComponentPrefix.NONE);
			}
		}
	}

	@Override
	public void enterType_prefix(Modelica.Type_prefixContext ctx) {
		setVariability(ctx.getText());
	}

	private void setVariability(String text) {
		String upperText = text.toUpperCase();
		switch (upperText) {
			case "PARAMETER" -> {
				componentBuilder.setVariability(ModelicaVariability.PARAMETER);
			}
			case "TYPE" -> {
				componentBuilder.setVariability(ModelicaVariability.TYPE);
			}
			case "INPUT" -> {
				componentBuilder.setVariability(ModelicaVariability.INPUT);
			}
			case "OUTPUT" -> {
				componentBuilder.setVariability(ModelicaVariability.OUTPUT);
			}
			default -> {
				componentBuilder.setVariability(ModelicaVariability.VARIABLE);
			}
		}
	}

	@Override
	public void enterModification_expression(Modelica.Modification_expressionContext ctx) {
		if (sectionsStack.peek() == ModelicaFileSection.COMPONENT_DECLARATION) {
			componentBuilder.setValue(ctx.getText());
		}
	}

	@Override
	public void enterComponent_declaration(Modelica.Component_declarationContext ctx) {
		sectionsStack.add(ModelicaFileSection.COMPONENT_DECLARATION);
		componentNames.add(ctx.declaration().IDENT().getText());
		componentBuilder.setComponentName(ctx.declaration().IDENT().getText());
	}

	@Override
	public void exitComponent_declaration(Modelica.Component_declarationContext ctx) {
		componentNames.pop();
		sectionsStack.pop();
		components.add(componentBuilder.createDeclaration());
		componentBuilder.reset();
	}

	@Override
	public void enterClass_definition(Modelica.Class_definitionContext ctx) {
		sectionsStack.add(ModelicaFileSection.CLASS_DEFINITION);
		ModelicaClassType classType = resolveModelicaClassType(ctx.class_prefixes().getText());
		declarationBuilder.setType(classType);
	}

	@Override
	public void enterShort_class_specifier(Modelica.Short_class_specifierContext ctx) {
		if (sectionsStack.peek() == ModelicaFileSection.CLASS_DEFINITION) {
			declarationBuilder.setDeclarationName(ctx.IDENT().getText());
		}
	}

	@Override
	public void enterType_specifier(Modelica.Type_specifierContext ctx) {
		if (sectionsStack.peek() == ModelicaFileSection.CLASS_DEFINITION) {
			declarationBuilder.setDeclarationClass(ctx.getText());
		}
	}

	@Override
	public void exitClass_definition(Modelica.Class_definitionContext ctx) {
		declarations.add(declarationBuilder.build());
		declarationBuilder.reset();
		sectionsStack.pop();
	}

	@Override
	public void enterElement_modification(Modelica.Element_modificationContext ctx) {
		if (isNotAnnotation()) {
			sectionsStack.add(ModelicaFileSection.COMPONENT_MODIFICATION);
			componentNames.add(ctx.name().getText());
		}
	}

	@Override
	public void enterModification(Modelica.ModificationContext ctx) {
		if (sectionsStack.peek() == ModelicaFileSection.COMPONENT_MODIFICATION) {
			Modification modification = new Modification(String.join(".", componentNames), ctx.getText(), "");
			modifications.add(modification);
		}
	}

	@Override
	public void exitElement_modification(Modelica.Element_modificationContext ctx) {
		if (isNotAnnotation()) componentNames.pop();
	}

	@Override
	public void enterElement_modification_or_replaceable(Modelica.Element_modification_or_replaceableContext ctx) {
		if (isNotAnnotation()) {
			sectionsStack.add(ModelicaFileSection.COMPONENT_MODIFICATION);
		}
	}

	@Override
	public void exitElement_modification_or_replaceable(Modelica.Element_modification_or_replaceableContext ctx) {
		if (isNotAnnotation()) {
			sectionsStack.pop();
		}
	}

	@Override
	public void enterImport_clause(Modelica.Import_clauseContext ctx) {
		importedClasses.add(ctx.name().getText());
	}

	@Override
	public void enterAnnotation(Modelica.AnnotationContext ctx) {
		sectionsStack.add(ModelicaFileSection.ANNOTATION);
	}

	@Override
	public void exitAnnotation(Modelica.AnnotationContext ctx) {
		sectionsStack.pop();
	}

	@Override
	public void enterExtends_clause(Modelica.Extends_clauseContext ctx) {
		extendingClasses.add(ctx.type_specifier().getText());
	}

	private boolean isNotAnnotation() {
		return sectionsStack.peek() != ModelicaFileSection.ANNOTATION;
	}
}
