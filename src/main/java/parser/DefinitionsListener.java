package parser;

import lombok.Getter;
import modelica.ClassTypeProvider;
import modelica.ModelicaClassType;
import modelica.ModelicaFileSection;
import modelica.ModelicaVariability;
import objects.definitions.Declaration;
import objects.definitions.DeclarationBuilder;
import objects.definitions.Modification;

import java.util.ArrayList;
import java.util.Stack;

public class DefinitionsListener extends ModelicaBaseListener {

	private final DeclarationBuilder declarationBuilder = new DeclarationBuilder();
	@Getter
	public ModelicaClassType modelicaClassType;
	@Getter
	ArrayList<Declaration> definitions = new ArrayList<>();
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
		declarationBuilder.reset();
		currentClassName = ctx.type_specifier().IDENT().getFirst().getText();
		declarationBuilder.setClassName(currentClassName);
	}


	@Override
	public void enterType_prefix(Modelica.Type_prefixContext ctx) {
		setVariability(ctx.getText());
	}

	private void setVariability(String text) {
		switch (text.toUpperCase()) {
			case "PARAMETER":
				declarationBuilder.setVariability(ModelicaVariability.PARAMETER);
			case "TYPE":
				declarationBuilder.setVariability(ModelicaVariability.TYPE);
			case "INPUT":
				declarationBuilder.setVariability(ModelicaVariability.INPUT);
			case "OUTPUT":
				declarationBuilder.setVariability(ModelicaVariability.OUTPUT);
			default:
				declarationBuilder.setVariability(ModelicaVariability.VARIABLE);
		}
	}

	@Override
	public void enterModification_expression(Modelica.Modification_expressionContext ctx) {
		if (sectionsStack.peek() == ModelicaFileSection.COMPONENT_DECLARATION) {
			declarationBuilder.setValue(ctx.getText());
		}
	}

	@Override
	public void enterComponent_declaration(Modelica.Component_declarationContext ctx) {
		sectionsStack.add(ModelicaFileSection.COMPONENT_DECLARATION);
		componentNames.add(ctx.declaration().IDENT().getText());
		declarationBuilder.setComponentName(ctx.declaration().IDENT().getText());
	}

	@Override
	public void exitComponent_declaration(Modelica.Component_declarationContext ctx) {
		componentNames.pop();
		sectionsStack.pop();
		definitions.add(declarationBuilder.createDeclaration());
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

	private boolean isNotAnnotation() {
		return sectionsStack.peek() != ModelicaFileSection.ANNOTATION;
	}
}
