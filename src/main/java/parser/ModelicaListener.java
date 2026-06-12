package parser;// Generated from Modelica.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Modelica}.
 */
public interface ModelicaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Modelica#stored_definition}.
	 * @param ctx the parse tree
	 */
	void enterStored_definition(Modelica.Stored_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#stored_definition}.
	 * @param ctx the parse tree
	 */
	void exitStored_definition(Modelica.Stored_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#class_definition}.
	 * @param ctx the parse tree
	 */
	void enterClass_definition(Modelica.Class_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#class_definition}.
	 * @param ctx the parse tree
	 */
	void exitClass_definition(Modelica.Class_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#class_prefixes}.
	 * @param ctx the parse tree
	 */
	void enterClass_prefixes(Modelica.Class_prefixesContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#class_prefixes}.
	 * @param ctx the parse tree
	 */
	void exitClass_prefixes(Modelica.Class_prefixesContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#class_specifier}.
	 * @param ctx the parse tree
	 */
	void enterClass_specifier(Modelica.Class_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#class_specifier}.
	 * @param ctx the parse tree
	 */
	void exitClass_specifier(Modelica.Class_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#long_class_specifier}.
	 * @param ctx the parse tree
	 */
	void enterLong_class_specifier(Modelica.Long_class_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#long_class_specifier}.
	 * @param ctx the parse tree
	 */
	void exitLong_class_specifier(Modelica.Long_class_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#end_clause}.
	 * @param ctx the parse tree
	 */
	void enterEnd_clause(Modelica.End_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#end_clause}.
	 * @param ctx the parse tree
	 */
	void exitEnd_clause(Modelica.End_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#short_class_specifier}.
	 * @param ctx the parse tree
	 */
	void enterShort_class_specifier(Modelica.Short_class_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#short_class_specifier}.
	 * @param ctx the parse tree
	 */
	void exitShort_class_specifier(Modelica.Short_class_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#der_class_specifier}.
	 * @param ctx the parse tree
	 */
	void enterDer_class_specifier(Modelica.Der_class_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#der_class_specifier}.
	 * @param ctx the parse tree
	 */
	void exitDer_class_specifier(Modelica.Der_class_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#base_prefix}.
	 * @param ctx the parse tree
	 */
	void enterBase_prefix(Modelica.Base_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#base_prefix}.
	 * @param ctx the parse tree
	 */
	void exitBase_prefix(Modelica.Base_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#enumerations}.
	 * @param ctx the parse tree
	 */
	void enterEnumerations(Modelica.EnumerationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#enumerations}.
	 * @param ctx the parse tree
	 */
	void exitEnumerations(Modelica.EnumerationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#enum_list}.
	 * @param ctx the parse tree
	 */
	void enterEnum_list(Modelica.Enum_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#enum_list}.
	 * @param ctx the parse tree
	 */
	void exitEnum_list(Modelica.Enum_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#enumeration_literal}.
	 * @param ctx the parse tree
	 */
	void enterEnumeration_literal(Modelica.Enumeration_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#enumeration_literal}.
	 * @param ctx the parse tree
	 */
	void exitEnumeration_literal(Modelica.Enumeration_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#composition}.
	 * @param ctx the parse tree
	 */
	void enterComposition(Modelica.CompositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#composition}.
	 * @param ctx the parse tree
	 */
	void exitComposition(Modelica.CompositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#class_annotation}.
	 * @param ctx the parse tree
	 */
	void enterClass_annotation(Modelica.Class_annotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#class_annotation}.
	 * @param ctx the parse tree
	 */
	void exitClass_annotation(Modelica.Class_annotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#external_element}.
	 * @param ctx the parse tree
	 */
	void enterExternal_element(Modelica.External_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#external_element}.
	 * @param ctx the parse tree
	 */
	void exitExternal_element(Modelica.External_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#language_specification}.
	 * @param ctx the parse tree
	 */
	void enterLanguage_specification(Modelica.Language_specificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#language_specification}.
	 * @param ctx the parse tree
	 */
	void exitLanguage_specification(Modelica.Language_specificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#external_function_call}.
	 * @param ctx the parse tree
	 */
	void enterExternal_function_call(Modelica.External_function_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#external_function_call}.
	 * @param ctx the parse tree
	 */
	void exitExternal_function_call(Modelica.External_function_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#external_function_args}.
	 * @param ctx the parse tree
	 */
	void enterExternal_function_args(Modelica.External_function_argsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#external_function_args}.
	 * @param ctx the parse tree
	 */
	void exitExternal_function_args(Modelica.External_function_argsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#initial_element_list}.
	 * @param ctx the parse tree
	 */
	void enterInitial_element_list(Modelica.Initial_element_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#initial_element_list}.
	 * @param ctx the parse tree
	 */
	void exitInitial_element_list(Modelica.Initial_element_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#public_element_list}.
	 * @param ctx the parse tree
	 */
	void enterPublic_element_list(Modelica.Public_element_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#public_element_list}.
	 * @param ctx the parse tree
	 */
	void exitPublic_element_list(Modelica.Public_element_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#protected_element_list}.
	 * @param ctx the parse tree
	 */
	void enterProtected_element_list(Modelica.Protected_element_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#protected_element_list}.
	 * @param ctx the parse tree
	 */
	void exitProtected_element_list(Modelica.Protected_element_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#element_list}.
	 * @param ctx the parse tree
	 */
	void enterElement_list(Modelica.Element_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#element_list}.
	 * @param ctx the parse tree
	 */
	void exitElement_list(Modelica.Element_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(Modelica.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(Modelica.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#import_clause}.
	 * @param ctx the parse tree
	 */
	void enterImport_clause(Modelica.Import_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#import_clause}.
	 * @param ctx the parse tree
	 */
	void exitImport_clause(Modelica.Import_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#import_list}.
	 * @param ctx the parse tree
	 */
	void enterImport_list(Modelica.Import_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#import_list}.
	 * @param ctx the parse tree
	 */
	void exitImport_list(Modelica.Import_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#declaration_clause}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration_clause(Modelica.Declaration_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#declaration_clause}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration_clause(Modelica.Declaration_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#component_prefix}.
	 * @param ctx the parse tree
	 */
	void enterComponent_prefix(Modelica.Component_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#component_prefix}.
	 * @param ctx the parse tree
	 */
	void exitComponent_prefix(Modelica.Component_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#extends_clause}.
	 * @param ctx the parse tree
	 */
	void enterExtends_clause(Modelica.Extends_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#extends_clause}.
	 * @param ctx the parse tree
	 */
	void exitExtends_clause(Modelica.Extends_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#constraining_clause}.
	 * @param ctx the parse tree
	 */
	void enterConstraining_clause(Modelica.Constraining_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#constraining_clause}.
	 * @param ctx the parse tree
	 */
	void exitConstraining_clause(Modelica.Constraining_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#class_or_inheritance_modification}.
	 * @param ctx the parse tree
	 */
	void enterClass_or_inheritance_modification(Modelica.Class_or_inheritance_modificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#class_or_inheritance_modification}.
	 * @param ctx the parse tree
	 */
	void exitClass_or_inheritance_modification(Modelica.Class_or_inheritance_modificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#argument_or_inheritance_modification_list}.
	 * @param ctx the parse tree
	 */
	void enterArgument_or_inheritance_modification_list(Modelica.Argument_or_inheritance_modification_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#argument_or_inheritance_modification_list}.
	 * @param ctx the parse tree
	 */
	void exitArgument_or_inheritance_modification_list(Modelica.Argument_or_inheritance_modification_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#inheritance_modification}.
	 * @param ctx the parse tree
	 */
	void enterInheritance_modification(Modelica.Inheritance_modificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#inheritance_modification}.
	 * @param ctx the parse tree
	 */
	void exitInheritance_modification(Modelica.Inheritance_modificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#component_clause}.
	 * @param ctx the parse tree
	 */
	void enterComponent_clause(Modelica.Component_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#component_clause}.
	 * @param ctx the parse tree
	 */
	void exitComponent_clause(Modelica.Component_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#type_prefix}.
	 * @param ctx the parse tree
	 */
	void enterType_prefix(Modelica.Type_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#type_prefix}.
	 * @param ctx the parse tree
	 */
	void exitType_prefix(Modelica.Type_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#component_list}.
	 * @param ctx the parse tree
	 */
	void enterComponent_list(Modelica.Component_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#component_list}.
	 * @param ctx the parse tree
	 */
	void exitComponent_list(Modelica.Component_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#component_declaration}.
	 * @param ctx the parse tree
	 */
	void enterComponent_declaration(Modelica.Component_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#component_declaration}.
	 * @param ctx the parse tree
	 */
	void exitComponent_declaration(Modelica.Component_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(Modelica.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(Modelica.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#modification}.
	 * @param ctx the parse tree
	 */
	void enterModification(Modelica.ModificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#modification}.
	 * @param ctx the parse tree
	 */
	void exitModification(Modelica.ModificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#modification_expression}.
	 * @param ctx the parse tree
	 */
	void enterModification_expression(Modelica.Modification_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#modification_expression}.
	 * @param ctx the parse tree
	 */
	void exitModification_expression(Modelica.Modification_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#class_modification}.
	 * @param ctx the parse tree
	 */
	void enterClass_modification(Modelica.Class_modificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#class_modification}.
	 * @param ctx the parse tree
	 */
	void exitClass_modification(Modelica.Class_modificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#argument_list}.
	 * @param ctx the parse tree
	 */
	void enterArgument_list(Modelica.Argument_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#argument_list}.
	 * @param ctx the parse tree
	 */
	void exitArgument_list(Modelica.Argument_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(Modelica.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(Modelica.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#element_modification_or_replaceable}.
	 * @param ctx the parse tree
	 */
	void enterElement_modification_or_replaceable(Modelica.Element_modification_or_replaceableContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#element_modification_or_replaceable}.
	 * @param ctx the parse tree
	 */
	void exitElement_modification_or_replaceable(Modelica.Element_modification_or_replaceableContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#element_modification}.
	 * @param ctx the parse tree
	 */
	void enterElement_modification(Modelica.Element_modificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#element_modification}.
	 * @param ctx the parse tree
	 */
	void exitElement_modification(Modelica.Element_modificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#element_redeclaration}.
	 * @param ctx the parse tree
	 */
	void enterElement_redeclaration(Modelica.Element_redeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#element_redeclaration}.
	 * @param ctx the parse tree
	 */
	void exitElement_redeclaration(Modelica.Element_redeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#element_replaceable}.
	 * @param ctx the parse tree
	 */
	void enterElement_replaceable(Modelica.Element_replaceableContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#element_replaceable}.
	 * @param ctx the parse tree
	 */
	void exitElement_replaceable(Modelica.Element_replaceableContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#short_component_clause}.
	 * @param ctx the parse tree
	 */
	void enterShort_component_clause(Modelica.Short_component_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#short_component_clause}.
	 * @param ctx the parse tree
	 */
	void exitShort_component_clause(Modelica.Short_component_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#short_component_declaration}.
	 * @param ctx the parse tree
	 */
	void enterShort_component_declaration(Modelica.Short_component_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#short_component_declaration}.
	 * @param ctx the parse tree
	 */
	void exitShort_component_declaration(Modelica.Short_component_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#short_definition}.
	 * @param ctx the parse tree
	 */
	void enterShort_definition(Modelica.Short_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#short_definition}.
	 * @param ctx the parse tree
	 */
	void exitShort_definition(Modelica.Short_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#equation_section}.
	 * @param ctx the parse tree
	 */
	void enterEquation_section(Modelica.Equation_sectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#equation_section}.
	 * @param ctx the parse tree
	 */
	void exitEquation_section(Modelica.Equation_sectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#algorithm_section}.
	 * @param ctx the parse tree
	 */
	void enterAlgorithm_section(Modelica.Algorithm_sectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#algorithm_section}.
	 * @param ctx the parse tree
	 */
	void exitAlgorithm_section(Modelica.Algorithm_sectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#equation_list}.
	 * @param ctx the parse tree
	 */
	void enterEquation_list(Modelica.Equation_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#equation_list}.
	 * @param ctx the parse tree
	 */
	void exitEquation_list(Modelica.Equation_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#statement_list}.
	 * @param ctx the parse tree
	 */
	void enterStatement_list(Modelica.Statement_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#statement_list}.
	 * @param ctx the parse tree
	 */
	void exitStatement_list(Modelica.Statement_listContext ctx);

	/**
	 * Enter a parse tree produced by {@link Modelica#assertion}.
	 *
	 * @param ctx the parse tree
	 */
	void enterAssertion(Modelica.AssertionContext ctx);

	/**
	 * Exit a parse tree produced by {@link Modelica#assertion}.
	 *
	 * @param ctx the parse tree
	 */
	void exitAssertion(Modelica.AssertionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#equation}.
	 * @param ctx the parse tree
	 */
	void enterEquation(Modelica.EquationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#equation}.
	 * @param ctx the parse tree
	 */
	void exitEquation(Modelica.EquationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(Modelica.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(Modelica.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#if_equation}.
	 * @param ctx the parse tree
	 */
	void enterIf_equation(Modelica.If_equationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#if_equation}.
	 * @param ctx the parse tree
	 */
	void exitIf_equation(Modelica.If_equationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#conditional_equations}.
	 * @param ctx the parse tree
	 */
	void enterConditional_equations(Modelica.Conditional_equationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#conditional_equations}.
	 * @param ctx the parse tree
	 */
	void exitConditional_equations(Modelica.Conditional_equationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(Modelica.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(Modelica.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#if_branch}.
	 * @param ctx the parse tree
	 */
	void enterIf_branch(Modelica.If_branchContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#if_branch}.
	 * @param ctx the parse tree
	 */
	void exitIf_branch(Modelica.If_branchContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#elseif_branch}.
	 * @param ctx the parse tree
	 */
	void enterElseif_branch(Modelica.Elseif_branchContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#elseif_branch}.
	 * @param ctx the parse tree
	 */
	void exitElseif_branch(Modelica.Elseif_branchContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#else_branch}.
	 * @param ctx the parse tree
	 */
	void enterElse_branch(Modelica.Else_branchContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#else_branch}.
	 * @param ctx the parse tree
	 */
	void exitElse_branch(Modelica.Else_branchContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#conditional_statements}.
	 * @param ctx the parse tree
	 */
	void enterConditional_statements(Modelica.Conditional_statementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#conditional_statements}.
	 * @param ctx the parse tree
	 */
	void exitConditional_statements(Modelica.Conditional_statementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#for_equation}.
	 * @param ctx the parse tree
	 */
	void enterFor_equation(Modelica.For_equationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#for_equation}.
	 * @param ctx the parse tree
	 */
	void exitFor_equation(Modelica.For_equationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#for_statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_statement(Modelica.For_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#for_statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_statement(Modelica.For_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#for_indices}.
	 * @param ctx the parse tree
	 */
	void enterFor_indices(Modelica.For_indicesContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#for_indices}.
	 * @param ctx the parse tree
	 */
	void exitFor_indices(Modelica.For_indicesContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#for_index}.
	 * @param ctx the parse tree
	 */
	void enterFor_index(Modelica.For_indexContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#for_index}.
	 * @param ctx the parse tree
	 */
	void exitFor_index(Modelica.For_indexContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#while_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statement(Modelica.While_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#while_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statement(Modelica.While_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#when_equation}.
	 * @param ctx the parse tree
	 */
	void enterWhen_equation(Modelica.When_equationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#when_equation}.
	 * @param ctx the parse tree
	 */
	void exitWhen_equation(Modelica.When_equationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#when_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhen_statement(Modelica.When_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#when_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhen_statement(Modelica.When_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#when_branch}.
	 * @param ctx the parse tree
	 */
	void enterWhen_branch(Modelica.When_branchContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#when_branch}.
	 * @param ctx the parse tree
	 */
	void exitWhen_branch(Modelica.When_branchContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#elsewhen_branch}.
	 * @param ctx the parse tree
	 */
	void enterElsewhen_branch(Modelica.Elsewhen_branchContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#elsewhen_branch}.
	 * @param ctx the parse tree
	 */
	void exitElsewhen_branch(Modelica.Elsewhen_branchContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#connect_equation}.
	 * @param ctx the parse tree
	 */
	void enterConnect_equation(Modelica.Connect_equationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#connect_equation}.
	 * @param ctx the parse tree
	 */
	void exitConnect_equation(Modelica.Connect_equationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#connected_components}.
	 * @param ctx the parse tree
	 */
	void enterConnected_components(Modelica.Connected_componentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#connected_components}.
	 * @param ctx the parse tree
	 */
	void exitConnected_components(Modelica.Connected_componentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(Modelica.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(Modelica.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#if_expression}.
	 * @param ctx the parse tree
	 */
	void enterIf_expression(Modelica.If_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#if_expression}.
	 * @param ctx the parse tree
	 */
	void exitIf_expression(Modelica.If_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#if_eval}.
	 * @param ctx the parse tree
	 */
	void enterIf_eval(Modelica.If_evalContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#if_eval}.
	 * @param ctx the parse tree
	 */
	void exitIf_eval(Modelica.If_evalContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#elseif_eval}.
	 * @param ctx the parse tree
	 */
	void enterElseif_eval(Modelica.Elseif_evalContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#elseif_eval}.
	 * @param ctx the parse tree
	 */
	void exitElseif_eval(Modelica.Elseif_evalContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#else_eval}.
	 * @param ctx the parse tree
	 */
	void enterElse_eval(Modelica.Else_evalContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#else_eval}.
	 * @param ctx the parse tree
	 */
	void exitElse_eval(Modelica.Else_evalContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#conditional_expression}.
	 * @param ctx the parse tree
	 */
	void enterConditional_expression(Modelica.Conditional_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#conditional_expression}.
	 * @param ctx the parse tree
	 */
	void exitConditional_expression(Modelica.Conditional_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#simple_expression}.
	 * @param ctx the parse tree
	 */
	void enterSimple_expression(Modelica.Simple_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#simple_expression}.
	 * @param ctx the parse tree
	 */
	void exitSimple_expression(Modelica.Simple_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#logical_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_expression(Modelica.Logical_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#logical_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_expression(Modelica.Logical_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#or_operator}.
	 * @param ctx the parse tree
	 */
	void enterOr_operator(Modelica.Or_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#or_operator}.
	 * @param ctx the parse tree
	 */
	void exitOr_operator(Modelica.Or_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#logical_term}.
	 * @param ctx the parse tree
	 */
	void enterLogical_term(Modelica.Logical_termContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#logical_term}.
	 * @param ctx the parse tree
	 */
	void exitLogical_term(Modelica.Logical_termContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#and_operator}.
	 * @param ctx the parse tree
	 */
	void enterAnd_operator(Modelica.And_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#and_operator}.
	 * @param ctx the parse tree
	 */
	void exitAnd_operator(Modelica.And_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#logical_factor}.
	 * @param ctx the parse tree
	 */
	void enterLogical_factor(Modelica.Logical_factorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#logical_factor}.
	 * @param ctx the parse tree
	 */
	void exitLogical_factor(Modelica.Logical_factorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelation(Modelica.RelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelation(Modelica.RelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#relational_operator}.
	 * @param ctx the parse tree
	 */
	void enterRelational_operator(Modelica.Relational_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#relational_operator}.
	 * @param ctx the parse tree
	 */
	void exitRelational_operator(Modelica.Relational_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#arithmetic_expression}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_expression(Modelica.Arithmetic_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#arithmetic_expression}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_expression(Modelica.Arithmetic_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#unary_expression}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expression(Modelica.Unary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#unary_expression}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expression(Modelica.Unary_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#unary_operand}.
	 * @param ctx the parse tree
	 */
	void enterUnary_operand(Modelica.Unary_operandContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#unary_operand}.
	 * @param ctx the parse tree
	 */
	void exitUnary_operand(Modelica.Unary_operandContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#add_operator}.
	 * @param ctx the parse tree
	 */
	void enterAdd_operator(Modelica.Add_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#add_operator}.
	 * @param ctx the parse tree
	 */
	void exitAdd_operator(Modelica.Add_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(Modelica.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(Modelica.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#mul_operator}.
	 * @param ctx the parse tree
	 */
	void enterMul_operator(Modelica.Mul_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#mul_operator}.
	 * @param ctx the parse tree
	 */
	void exitMul_operator(Modelica.Mul_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(Modelica.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(Modelica.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#exp_operator}.
	 * @param ctx the parse tree
	 */
	void enterExp_operator(Modelica.Exp_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#exp_operator}.
	 * @param ctx the parse tree
	 */
	void exitExp_operator(Modelica.Exp_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(Modelica.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(Modelica.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#type_specifier}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier(Modelica.Type_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#type_specifier}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier(Modelica.Type_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#name}.
	 * @param ctx the parse tree
	 */
	void enterName(Modelica.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#name}.
	 * @param ctx the parse tree
	 */
	void exitName(Modelica.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#component_reference}.
	 * @param ctx the parse tree
	 */
	void enterComponent_reference(Modelica.Component_referenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#component_reference}.
	 * @param ctx the parse tree
	 */
	void exitComponent_reference(Modelica.Component_referenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#function_call_args}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_args(Modelica.Function_call_argsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#function_call_args}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_args(Modelica.Function_call_argsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#function_arguments}.
	 * @param ctx the parse tree
	 */
	void enterFunction_arguments(Modelica.Function_argumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#function_arguments}.
	 * @param ctx the parse tree
	 */
	void exitFunction_arguments(Modelica.Function_argumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#named_arguments}.
	 * @param ctx the parse tree
	 */
	void enterNamed_arguments(Modelica.Named_argumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#named_arguments}.
	 * @param ctx the parse tree
	 */
	void exitNamed_arguments(Modelica.Named_argumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#named_argument}.
	 * @param ctx the parse tree
	 */
	void enterNamed_argument(Modelica.Named_argumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#named_argument}.
	 * @param ctx the parse tree
	 */
	void exitNamed_argument(Modelica.Named_argumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#function_argument}.
	 * @param ctx the parse tree
	 */
	void enterFunction_argument(Modelica.Function_argumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#function_argument}.
	 * @param ctx the parse tree
	 */
	void exitFunction_argument(Modelica.Function_argumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#function_partial_application}.
	 * @param ctx the parse tree
	 */
	void enterFunction_partial_application(Modelica.Function_partial_applicationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#function_partial_application}.
	 * @param ctx the parse tree
	 */
	void exitFunction_partial_application(Modelica.Function_partial_applicationContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#output_expression_list}.
	 * @param ctx the parse tree
	 */
	void enterOutput_expression_list(Modelica.Output_expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#output_expression_list}.
	 * @param ctx the parse tree
	 */
	void exitOutput_expression_list(Modelica.Output_expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#expression_list}.
	 * @param ctx the parse tree
	 */
	void enterExpression_list(Modelica.Expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#expression_list}.
	 * @param ctx the parse tree
	 */
	void exitExpression_list(Modelica.Expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#array_arguments}.
	 * @param ctx the parse tree
	 */
	void enterArray_arguments(Modelica.Array_argumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#array_arguments}.
	 * @param ctx the parse tree
	 */
	void exitArray_arguments(Modelica.Array_argumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#array_subscripts}.
	 * @param ctx the parse tree
	 */
	void enterArray_subscripts(Modelica.Array_subscriptsContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#array_subscripts}.
	 * @param ctx the parse tree
	 */
	void exitArray_subscripts(Modelica.Array_subscriptsContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#subscript}.
	 * @param ctx the parse tree
	 */
	void enterSubscript(Modelica.SubscriptContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#subscript}.
	 * @param ctx the parse tree
	 */
	void exitSubscript(Modelica.SubscriptContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#description}.
	 * @param ctx the parse tree
	 */
	void enterDescription(Modelica.DescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#description}.
	 * @param ctx the parse tree
	 */
	void exitDescription(Modelica.DescriptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#description_string}.
	 * @param ctx the parse tree
	 */
	void enterDescription_string(Modelica.Description_stringContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#description_string}.
	 * @param ctx the parse tree
	 */
	void exitDescription_string(Modelica.Description_stringContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#cat_operator}.
	 * @param ctx the parse tree
	 */
	void enterCat_operator(Modelica.Cat_operatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#cat_operator}.
	 * @param ctx the parse tree
	 */
	void exitCat_operator(Modelica.Cat_operatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link Modelica#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(Modelica.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link Modelica#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(Modelica.AnnotationContext ctx);
}