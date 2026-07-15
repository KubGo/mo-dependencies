package parser;

import lombok.Getter;
import modelica.ComponentPrefix;
import modelica.ModelicaClassType;
import modelica.ModelicaFileSection;
import modelica.ModelicaVariability;
import objects.modelica.*;

import java.util.ArrayList;
import java.util.Stack;

import static modelica.ClassTypeProvider.resolveModelicaClassType;

public class DefinitionsListener extends ModelicaBaseListener {

	private final ComponentBuilder componentBuilder = new ComponentBuilder();
	private final DeclarationBuilder declarationBuilder = new DeclarationBuilder();
	private final ModificationBuilder modificationBuilder = new ModificationBuilder();

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
	}

	@Override
	public void enterLong_class_specifier(Modelica.Long_class_specifierContext ctx) {
		if (isCurrentSection(ModelicaFileSection.DECLARATIVE)) {
			name = ctx.IDENT().getText();
		}
	}



	@Override
	public void enterDeclaration_clause(Modelica.Declaration_clauseContext ctx) {
		sectionsStack.add(ModelicaFileSection.COMPONENT_DECLARATION);
	}

	@Override
	public void exitDeclaration_clause(Modelica.Declaration_clauseContext ctx) {
		if (componentBuilder.isReady()) {
			components.add(componentBuilder.build());
			componentBuilder.reset();
		} else if (declarationBuilder.isReady()) {
			declarations.add(declarationBuilder.build());
			declarationBuilder.reset();
		}
		componentNames.clear();
		sectionsStack.pop();
	}

	@Override
	public void enterComponent_prefix(Modelica.Component_prefixContext ctx) {
		setComponentPrefix(ctx.getText());
	}

	private void setComponentPrefix(String text) {
		switch (text.toUpperCase()) {
			case ("REPLACEABLE") -> componentBuilder.setComponentPrefix(ComponentPrefix.REPLACEABLE);
			case ("REDECLARE REPLACEABLE") ->
					componentBuilder.setComponentPrefix(ComponentPrefix.REDECLARE_REPLACEABLE);
			case ("REDECLARE") -> componentBuilder.setComponentPrefix(ComponentPrefix.REDECLARE);
			case ("INNER") -> componentBuilder.setComponentPrefix(ComponentPrefix.INNER);
			case ("OUTER") -> componentBuilder.setComponentPrefix(ComponentPrefix.OUTER);
			case ("FINAL") -> componentBuilder.setComponentPrefix(ComponentPrefix.FINAL);
			default -> componentBuilder.setComponentPrefix(ComponentPrefix.NONE);
		}
	}

	@Override
	public void enterType_prefix(Modelica.Type_prefixContext ctx) {
		setVariability(ctx.getText());
	}

	private void setVariability(String text) {
		String upperText = text.toUpperCase();
		switch (upperText) {
			case "PARAMETER" -> componentBuilder.setVariability(ModelicaVariability.PARAMETER);
			case "TYPE" -> componentBuilder.setVariability(ModelicaVariability.TYPE);
			case "INPUT" -> componentBuilder.setVariability(ModelicaVariability.INPUT);
			case "OUTPUT" -> componentBuilder.setVariability(ModelicaVariability.OUTPUT);
			default -> componentBuilder.setVariability(ModelicaVariability.VARIABLE);
		}
	}

	@Override
	public void enterClass_modification(Modelica.Class_modificationContext ctx) {
		if (isNotAnnotation()) {
			sectionsStack.add(ModelicaFileSection.COMPONENT_MODIFICATION);
		}
	}

	@Override
	public void exitClass_modification(Modelica.Class_modificationContext ctx) {
		if (isNotAnnotation()) {
			sectionsStack.pop();
		}
	}

	@Override
	public void enterModification_expression(Modelica.Modification_expressionContext ctx) {
		if (isCurrentSection(ModelicaFileSection.COMPONENT_DECLARATION)) {
			componentBuilder.setValue(ctx.getText());
		}
		if (isCurrentSection(ModelicaFileSection.COMPONENT_MODIFICATION)) {
			modificationBuilder.setValue(ctx.getText());
			modificationBuilder.setComponent(String.join(".", componentNames));
		}
	}

	@Override
	public void enterDeclaration(Modelica.DeclarationContext ctx) {
		if (isCurrentSection(ModelicaFileSection.COMPONENT_MODIFICATION)) {
			componentNames.add(ctx.getText());
			modificationBuilder.setComponent(componentNames);
		}
	}

	@Override
	public void enterComponent_declaration(Modelica.Component_declarationContext ctx) {
		String componentName = ctx.declaration().IDENT().getText();
		componentBuilder.setComponentName(componentName);
		componentNames.add(componentName);
	}

	@Override
	public void enterClass_definition(Modelica.Class_definitionContext ctx) {
		sectionsStack.add(ModelicaFileSection.CLASS_DEFINITION);
		ModelicaClassType classType = resolveModelicaClassType(ctx.class_prefixes().getText());
		declarationBuilder.setType(classType);
	}

	@Override
	public void enterShort_class_specifier(Modelica.Short_class_specifierContext ctx) {
		if (isCurrentSection(ModelicaFileSection.CLASS_DEFINITION)) {
			declarationBuilder.setDeclarationName(ctx.IDENT().getText());
		}
		if (isCurrentSection(ModelicaFileSection.COMPONENT_MODIFICATION)) {
			componentNames.add(ctx.IDENT().getText());
			modificationBuilder.setComponent(String.join(".", componentNames));
			modificationBuilder.setValue(ctx.type_specifier().getText());
			if (modificationBuilder.isReady()) {
				modifications.add(modificationBuilder.build());
			}
			modificationBuilder.reset();
		}
		if (isCurrentSection(ModelicaFileSection.DECLARATIVE)) {
			name = ctx.IDENT().getText();
		}
	}

	@Override
	public void exitShort_class_specifier(Modelica.Short_class_specifierContext ctx) {
		if (isCurrentSection(ModelicaFileSection.COMPONENT_MODIFICATION)) {
			componentNames.pop();
		}
	}

	@Override
	public void exitElement_redeclaration(Modelica.Element_redeclarationContext ctx) {
		if (modificationBuilder.isReady()) {
			modifications.add(modificationBuilder.build());
			modificationBuilder.reset();
		}
	}

	@Override
	public void enterType_specifier(Modelica.Type_specifierContext ctx) {
		if (isCurrentSection(ModelicaFileSection.CLASS_DEFINITION)) {
			declarationBuilder.setDeclarationClass(ctx.getText());
		}
		if (isCurrentSection(ModelicaFileSection.CONSTRAINING_CLAUSE)) {
			componentBuilder.setConstrainingClass(ctx.getText());
		}
		if (isCurrentSection(ModelicaFileSection.COMPONENT_DECLARATION)) {
			componentBuilder.setClassName(ctx.getText());
		}
		if (isCurrentSection(ModelicaFileSection.EXTENDS_CLAUSE)) {
			extendingClasses.add(ctx.getText());
		}
		if (isCurrentSection(ModelicaFileSection.COMPONENT_MODIFICATION)) {
			modificationBuilder.setValue(ctx.getText());
		}
	}

	private boolean isCurrentSection(ModelicaFileSection expectedSection) {
		return sectionsStack.peek() == expectedSection;
	}

	@Override
	public void exitClass_definition(Modelica.Class_definitionContext ctx) {
		sectionsStack.pop();
	}

	@Override
	public void enterElement_modification(Modelica.Element_modificationContext ctx) {
		if (isCurrentSection(ModelicaFileSection.COMPONENT_MODIFICATION)) {
			componentNames.add(ctx.name().getText());
		}
	}

	@Override
	public void exitElement_modification(Modelica.Element_modificationContext ctx) {
		if (isCurrentSection(ModelicaFileSection.COMPONENT_MODIFICATION)) {
			if (modificationBuilder.isReady()) {
				modifications.add(modificationBuilder.build());
				modificationBuilder.reset();
			}
			componentNames.pop();
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
	public void enterClass_annotation(Modelica.Class_annotationContext ctx) {
		sectionsStack.add(ModelicaFileSection.ANNOTATION);
	}

	@Override
	public void exitClass_annotation(Modelica.Class_annotationContext ctx) {
		sectionsStack.pop();
	}

	@Override
	public void enterExtends_clause(Modelica.Extends_clauseContext ctx) {
		sectionsStack.add(ModelicaFileSection.EXTENDS_CLAUSE);
	}

	@Override
	public void exitExtends_clause(Modelica.Extends_clauseContext ctx) {
		sectionsStack.pop();
	}

	@Override
	public void enterConstraining_clause(Modelica.Constraining_clauseContext ctx) {
		sectionsStack.add(ModelicaFileSection.CONSTRAINING_CLAUSE);
	}

	@Override
	public void exitConstraining_clause(Modelica.Constraining_clauseContext ctx) {
		sectionsStack.pop();
	}
	private boolean isNotAnnotation() {
		return sectionsStack.peek() != ModelicaFileSection.ANNOTATION;
	}

	@Override
	public void enterElement_redeclaration(Modelica.Element_redeclarationContext ctx) {
		super.enterElement_redeclaration(ctx);
	}
}
