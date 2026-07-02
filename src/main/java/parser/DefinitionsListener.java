package parser;

import lombok.Getter;
import modelica.ClassTypeProvider;
import modelica.ModelicaClassType;
import modelica.ModelicaVariability;
import objects.definitions.Declaration;
import objects.definitions.DeclarationBuilder;
import objects.definitions.Modification;

import java.util.ArrayList;

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
	private boolean modification = false;

	/**
	 * Retrieves the package name and class type
	 *
	 * @param ctx the parse tree
	 */
	@Override
	public void enterStored_definition(Modelica.Stored_definitionContext ctx) {
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
		if (!modification) {
			declarationBuilder.setValue(ctx.getText());
		}
	}

	@Override
	public void enterComponent_declaration(Modelica.Component_declarationContext ctx) {
		declarationBuilder.setComponentName(ctx.declaration().IDENT().getText());
	}

	@Override
	public void exitComponent_declaration(Modelica.Component_declarationContext ctx) {
		definitions.add(declarationBuilder.createDeclaration());
	}

	@Override
	public void enterElement_modification(Modelica.Element_modificationContext ctx) {
		modification = true;

	}

	@Override
	public void exitElement_modification(Modelica.Element_modificationContext ctx) {
		modification = false;
	}

	@Override
	public void enterElement_modification_or_replaceable(Modelica.Element_modification_or_replaceableContext ctx) {
		modification = true;
	}

	@Override
	public void exitElement_modification_or_replaceable(Modelica.Element_modification_or_replaceableContext ctx) {
		modification = false;
	}

	@Override
	public void enterImport_clause(Modelica.Import_clauseContext ctx) {
		importedClasses.add(ctx.name().getText());
	}


}
