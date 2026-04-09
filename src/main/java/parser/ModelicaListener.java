package parser;// Generated from modelica.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ModelicaParser}.
 */
public interface ModelicaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#stored_definition}.
	 * @param ctx the parse tree
	 */
	void enterStored_definition(ModelicaParser.Stored_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#stored_definition}.
	 * @param ctx the parse tree
	 */
	void exitStored_definition(ModelicaParser.Stored_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#class_definition}.
	 * @param ctx the parse tree
	 */
	void enterClass_definition(ModelicaParser.Class_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#class_definition}.
	 * @param ctx the parse tree
	 */
	void exitClass_definition(ModelicaParser.Class_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#class_specifier}.
	 * @param ctx the parse tree
	 */
	void enterClass_specifier(ModelicaParser.Class_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#class_specifier}.
	 * @param ctx the parse tree
	 */
	void exitClass_specifier(ModelicaParser.Class_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#class_prefixes}.
	 * @param ctx the parse tree
	 */
	void enterClass_prefixes(ModelicaParser.Class_prefixesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#class_prefixes}.
	 * @param ctx the parse tree
	 */
	void exitClass_prefixes(ModelicaParser.Class_prefixesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#long_class_specifier}.
	 * @param ctx the parse tree
	 */
	void enterLong_class_specifier(ModelicaParser.Long_class_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#long_class_specifier}.
	 * @param ctx the parse tree
	 */
	void exitLong_class_specifier(ModelicaParser.Long_class_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#short_class_specifier}.
	 * @param ctx the parse tree
	 */
	void enterShort_class_specifier(ModelicaParser.Short_class_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#short_class_specifier}.
	 * @param ctx the parse tree
	 */
	void exitShort_class_specifier(ModelicaParser.Short_class_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#der_class_specifier}.
	 * @param ctx the parse tree
	 */
	void enterDer_class_specifier(ModelicaParser.Der_class_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#der_class_specifier}.
	 * @param ctx the parse tree
	 */
	void exitDer_class_specifier(ModelicaParser.Der_class_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#base_prefix}.
	 * @param ctx the parse tree
	 */
	void enterBase_prefix(ModelicaParser.Base_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#base_prefix}.
	 * @param ctx the parse tree
	 */
	void exitBase_prefix(ModelicaParser.Base_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#enum_list}.
	 * @param ctx the parse tree
	 */
	void enterEnum_list(ModelicaParser.Enum_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#enum_list}.
	 * @param ctx the parse tree
	 */
	void exitEnum_list(ModelicaParser.Enum_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#enumeration_literal}.
	 * @param ctx the parse tree
	 */
	void enterEnumeration_literal(ModelicaParser.Enumeration_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#enumeration_literal}.
	 * @param ctx the parse tree
	 */
	void exitEnumeration_literal(ModelicaParser.Enumeration_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#composition}.
	 * @param ctx the parse tree
	 */
	void enterComposition(ModelicaParser.CompositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#composition}.
	 * @param ctx the parse tree
	 */
	void exitComposition(ModelicaParser.CompositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#language_specification}.
	 * @param ctx the parse tree
	 */
	void enterLanguage_specification(ModelicaParser.Language_specificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#language_specification}.
	 * @param ctx the parse tree
	 */
	void exitLanguage_specification(ModelicaParser.Language_specificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#external_function_call}.
	 * @param ctx the parse tree
	 */
	void enterExternal_function_call(ModelicaParser.External_function_callContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#external_function_call}.
	 * @param ctx the parse tree
	 */
	void exitExternal_function_call(ModelicaParser.External_function_callContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#element_list}.
	 * @param ctx the parse tree
	 */
	void enterElement_list(ModelicaParser.Element_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#element_list}.
	 * @param ctx the parse tree
	 */
	void exitElement_list(ModelicaParser.Element_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(ModelicaParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(ModelicaParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#import_clause}.
	 * @param ctx the parse tree
	 */
	void enterImport_clause(ModelicaParser.Import_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#import_clause}.
	 * @param ctx the parse tree
	 */
	void exitImport_clause(ModelicaParser.Import_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#import_list}.
	 * @param ctx the parse tree
	 */
	void enterImport_list(ModelicaParser.Import_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#import_list}.
	 * @param ctx the parse tree
	 */
	void exitImport_list(ModelicaParser.Import_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#extends_clause}.
	 * @param ctx the parse tree
	 */
	void enterExtends_clause(ModelicaParser.Extends_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#extends_clause}.
	 * @param ctx the parse tree
	 */
	void exitExtends_clause(ModelicaParser.Extends_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#constraining_clause}.
	 * @param ctx the parse tree
	 */
	void enterConstraining_clause(ModelicaParser.Constraining_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#constraining_clause}.
	 * @param ctx the parse tree
	 */
	void exitConstraining_clause(ModelicaParser.Constraining_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#component_clause}.
	 * @param ctx the parse tree
	 */
	void enterComponent_clause(ModelicaParser.Component_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#component_clause}.
	 * @param ctx the parse tree
	 */
	void exitComponent_clause(ModelicaParser.Component_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#type_prefix}.
	 * @param ctx the parse tree
	 */
	void enterType_prefix(ModelicaParser.Type_prefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#type_prefix}.
	 * @param ctx the parse tree
	 */
	void exitType_prefix(ModelicaParser.Type_prefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier(ModelicaParser.Type_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier(ModelicaParser.Type_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#component_list}.
	 * @param ctx the parse tree
	 */
	void enterComponent_list(ModelicaParser.Component_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#component_list}.
	 * @param ctx the parse tree
	 */
	void exitComponent_list(ModelicaParser.Component_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#component_declaration}.
	 * @param ctx the parse tree
	 */
	void enterComponent_declaration(ModelicaParser.Component_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#component_declaration}.
	 * @param ctx the parse tree
	 */
	void exitComponent_declaration(ModelicaParser.Component_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#condition_attribute}.
	 * @param ctx the parse tree
	 */
	void enterCondition_attribute(ModelicaParser.Condition_attributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#condition_attribute}.
	 * @param ctx the parse tree
	 */
	void exitCondition_attribute(ModelicaParser.Condition_attributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ModelicaParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ModelicaParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#modification}.
	 * @param ctx the parse tree
	 */
	void enterModification(ModelicaParser.ModificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#modification}.
	 * @param ctx the parse tree
	 */
	void exitModification(ModelicaParser.ModificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#class_modification}.
	 * @param ctx the parse tree
	 */
	void enterClass_modification(ModelicaParser.Class_modificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#class_modification}.
	 * @param ctx the parse tree
	 */
	void exitClass_modification(ModelicaParser.Class_modificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#argument_list}.
	 * @param ctx the parse tree
	 */
	void enterArgument_list(ModelicaParser.Argument_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#argument_list}.
	 * @param ctx the parse tree
	 */
	void exitArgument_list(ModelicaParser.Argument_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(ModelicaParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(ModelicaParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#element_modification_or_replaceable}.
	 * @param ctx the parse tree
	 */
	void enterElement_modification_or_replaceable(ModelicaParser.Element_modification_or_replaceableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#element_modification_or_replaceable}.
	 * @param ctx the parse tree
	 */
	void exitElement_modification_or_replaceable(ModelicaParser.Element_modification_or_replaceableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#element_modification}.
	 * @param ctx the parse tree
	 */
	void enterElement_modification(ModelicaParser.Element_modificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#element_modification}.
	 * @param ctx the parse tree
	 */
	void exitElement_modification(ModelicaParser.Element_modificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#element_redeclaration}.
	 * @param ctx the parse tree
	 */
	void enterElement_redeclaration(ModelicaParser.Element_redeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#element_redeclaration}.
	 * @param ctx the parse tree
	 */
	void exitElement_redeclaration(ModelicaParser.Element_redeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#element_replaceable}.
	 * @param ctx the parse tree
	 */
	void enterElement_replaceable(ModelicaParser.Element_replaceableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#element_replaceable}.
	 * @param ctx the parse tree
	 */
	void exitElement_replaceable(ModelicaParser.Element_replaceableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#component_clause1}.
	 * @param ctx the parse tree
	 */
	void enterComponent_clause1(ModelicaParser.Component_clause1Context ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#component_clause1}.
	 * @param ctx the parse tree
	 */
	void exitComponent_clause1(ModelicaParser.Component_clause1Context ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#component_declaration1}.
	 * @param ctx the parse tree
	 */
	void enterComponent_declaration1(ModelicaParser.Component_declaration1Context ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#component_declaration1}.
	 * @param ctx the parse tree
	 */
	void exitComponent_declaration1(ModelicaParser.Component_declaration1Context ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#short_class_definition}.
	 * @param ctx the parse tree
	 */
	void enterShort_class_definition(ModelicaParser.Short_class_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#short_class_definition}.
	 * @param ctx the parse tree
	 */
	void exitShort_class_definition(ModelicaParser.Short_class_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#equation_section}.
	 * @param ctx the parse tree
	 */
	void enterEquation_section(ModelicaParser.Equation_sectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#equation_section}.
	 * @param ctx the parse tree
	 */
	void exitEquation_section(ModelicaParser.Equation_sectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#algorithm_section}.
	 * @param ctx the parse tree
	 */
	void enterAlgorithm_section(ModelicaParser.Algorithm_sectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#algorithm_section}.
	 * @param ctx the parse tree
	 */
	void exitAlgorithm_section(ModelicaParser.Algorithm_sectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterEquation(ModelicaParser.EquationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitEquation(ModelicaParser.EquationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ModelicaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ModelicaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#if_equation}.
	 * @param ctx the parse tree
	 */
	void enterIf_equation(ModelicaParser.If_equationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#if_equation}.
	 * @param ctx the parse tree
	 */
	void exitIf_equation(ModelicaParser.If_equationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(ModelicaParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(ModelicaParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#for_equation}.
	 * @param ctx the parse tree
	 */
	void enterFor_equation(ModelicaParser.For_equationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#for_equation}.
	 * @param ctx the parse tree
	 */
	void exitFor_equation(ModelicaParser.For_equationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#for_statement}.
	 * @param ctx the parse tree
	 */
	void enterFor_statement(ModelicaParser.For_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#for_statement}.
	 * @param ctx the parse tree
	 */
	void exitFor_statement(ModelicaParser.For_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#for_indices}.
	 * @param ctx the parse tree
	 */
	void enterFor_indices(ModelicaParser.For_indicesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#for_indices}.
	 * @param ctx the parse tree
	 */
	void exitFor_indices(ModelicaParser.For_indicesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#for_index}.
	 * @param ctx the parse tree
	 */
	void enterFor_index(ModelicaParser.For_indexContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#for_index}.
	 * @param ctx the parse tree
	 */
	void exitFor_index(ModelicaParser.For_indexContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhile_statement(ModelicaParser.While_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#while_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhile_statement(ModelicaParser.While_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#when_equation}.
	 * @param ctx the parse tree
	 */
	void enterWhen_equation(ModelicaParser.When_equationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#when_equation}.
	 * @param ctx the parse tree
	 */
	void exitWhen_equation(ModelicaParser.When_equationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#when_statement}.
	 * @param ctx the parse tree
	 */
	void enterWhen_statement(ModelicaParser.When_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#when_statement}.
	 * @param ctx the parse tree
	 */
	void exitWhen_statement(ModelicaParser.When_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#connect_clause}.
	 * @param ctx the parse tree
	 */
	void enterConnect_clause(ModelicaParser.Connect_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#connect_clause}.
	 * @param ctx the parse tree
	 */
	void exitConnect_clause(ModelicaParser.Connect_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ModelicaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ModelicaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#simple_expression}.
	 * @param ctx the parse tree
	 */
	void enterSimple_expression(ModelicaParser.Simple_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#simple_expression}.
	 * @param ctx the parse tree
	 */
	void exitSimple_expression(ModelicaParser.Simple_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#logical_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_expression(ModelicaParser.Logical_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#logical_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_expression(ModelicaParser.Logical_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#logical_term}.
	 * @param ctx the parse tree
	 */
	void enterLogical_term(ModelicaParser.Logical_termContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#logical_term}.
	 * @param ctx the parse tree
	 */
	void exitLogical_term(ModelicaParser.Logical_termContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#logical_factor}.
	 * @param ctx the parse tree
	 */
	void enterLogical_factor(ModelicaParser.Logical_factorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#logical_factor}.
	 * @param ctx the parse tree
	 */
	void exitLogical_factor(ModelicaParser.Logical_factorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#relation}.
	 * @param ctx the parse tree
	 */
	void enterRelation(ModelicaParser.RelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#relation}.
	 * @param ctx the parse tree
	 */
	void exitRelation(ModelicaParser.RelationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#rel_op}.
	 * @param ctx the parse tree
	 */
	void enterRel_op(ModelicaParser.Rel_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#rel_op}.
	 * @param ctx the parse tree
	 */
	void exitRel_op(ModelicaParser.Rel_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#arithmetic_expression}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_expression(ModelicaParser.Arithmetic_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#arithmetic_expression}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_expression(ModelicaParser.Arithmetic_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#add_op}.
	 * @param ctx the parse tree
	 */
	void enterAdd_op(ModelicaParser.Add_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#add_op}.
	 * @param ctx the parse tree
	 */
	void exitAdd_op(ModelicaParser.Add_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(ModelicaParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(ModelicaParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#mul_op}.
	 * @param ctx the parse tree
	 */
	void enterMul_op(ModelicaParser.Mul_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#mul_op}.
	 * @param ctx the parse tree
	 */
	void exitMul_op(ModelicaParser.Mul_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(ModelicaParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(ModelicaParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(ModelicaParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(ModelicaParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(ModelicaParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(ModelicaParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#component_reference}.
	 * @param ctx the parse tree
	 */
	void enterComponent_reference(ModelicaParser.Component_referenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#component_reference}.
	 * @param ctx the parse tree
	 */
	void exitComponent_reference(ModelicaParser.Component_referenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#function_call_args}.
	 * @param ctx the parse tree
	 */
	void enterFunction_call_args(ModelicaParser.Function_call_argsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#function_call_args}.
	 * @param ctx the parse tree
	 */
	void exitFunction_call_args(ModelicaParser.Function_call_argsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#function_arguments}.
	 * @param ctx the parse tree
	 */
	void enterFunction_arguments(ModelicaParser.Function_argumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#function_arguments}.
	 * @param ctx the parse tree
	 */
	void exitFunction_arguments(ModelicaParser.Function_argumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#named_arguments}.
	 * @param ctx the parse tree
	 */
	void enterNamed_arguments(ModelicaParser.Named_argumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#named_arguments}.
	 * @param ctx the parse tree
	 */
	void exitNamed_arguments(ModelicaParser.Named_argumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#named_argument}.
	 * @param ctx the parse tree
	 */
	void enterNamed_argument(ModelicaParser.Named_argumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#named_argument}.
	 * @param ctx the parse tree
	 */
	void exitNamed_argument(ModelicaParser.Named_argumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#function_argument}.
	 * @param ctx the parse tree
	 */
	void enterFunction_argument(ModelicaParser.Function_argumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#function_argument}.
	 * @param ctx the parse tree
	 */
	void exitFunction_argument(ModelicaParser.Function_argumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#output_expression_list}.
	 * @param ctx the parse tree
	 */
	void enterOutput_expression_list(ModelicaParser.Output_expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#output_expression_list}.
	 * @param ctx the parse tree
	 */
	void exitOutput_expression_list(ModelicaParser.Output_expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void enterExpression_list(ModelicaParser.Expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#expression_list}.
	 * @param ctx the parse tree
	 */
	void exitExpression_list(ModelicaParser.Expression_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#array_subscripts}.
	 * @param ctx the parse tree
	 */
	void enterArray_subscripts(ModelicaParser.Array_subscriptsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#array_subscripts}.
	 * @param ctx the parse tree
	 */
	void exitArray_subscripts(ModelicaParser.Array_subscriptsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#subscript_}.
	 * @param ctx the parse tree
	 */
	void enterSubscript_(ModelicaParser.Subscript_Context ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#subscript_}.
	 * @param ctx the parse tree
	 */
	void exitSubscript_(ModelicaParser.Subscript_Context ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#comment}.
	 * @param ctx the parse tree
	 */
	void enterComment(ModelicaParser.CommentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#comment}.
	 * @param ctx the parse tree
	 */
	void exitComment(ModelicaParser.CommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#string_comment}.
	 * @param ctx the parse tree
	 */
	void enterString_comment(ModelicaParser.String_commentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#string_comment}.
	 * @param ctx the parse tree
	 */
	void exitString_comment(ModelicaParser.String_commentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelicaParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(ModelicaParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelicaParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(ModelicaParser.AnnotationContext ctx);
}