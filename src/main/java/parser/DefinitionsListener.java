package parser;

import lombok.Getter;
import modelica.ClassTypeProvider;
import modelica.ModelicaClassType;
import modelica.ModelicaFileSection;
import modelica.ModelicaVariability;
import objects.definitions.Component;
import objects.definitions.ComponentBuilder;
import objects.definitions.Modification;

import java.util.ArrayList;
import java.util.Stack;

public class DefinitionsListener extends ModelicaBaseListener {

	private final ComponentBuilder componentBuilder = new ComponentBuilder();
	@Getter
	public ModelicaClassType modelicaClassType;
	@Getter
	ArrayList<Component> definitions = new ArrayList<>();
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
		modelicaClassType = ClassTypeProvider.getClassType(
				ctx.class_definition().getFirst().class_prefixes().getText());
		name = ctx.class_definition().getFirst().class_specifier().long_class_specifier().IDENT().getText();
	}


	@Override
	public void enterComponent_clause(Modelica.Component_clauseContext ctx) {
		componentBuilder.reset();
		currentClassName = ctx.type_specifier().getText();
		componentBuilder.setClassName(currentClassName);
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
		definitions.add(componentBuilder.createDeclaration());
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
