package modelica;

public class ClassTypeProvider {
	public static ModelicaClassType getClassType(String classPrefix) throws IllegalStateException {
		switch (classPrefix){
			case "model" -> {
				return ModelicaClassType.MODEL;
			}
			case "partialmodel" -> {
				return ModelicaClassType.PARTIAL_MODEL;
			}
			case "record" -> {
				return ModelicaClassType.RECORD;
			}
			case "partialrecord" -> {
				return ModelicaClassType.PARTIAL_RECORD;
			}
			case "function" -> {
				return ModelicaClassType.FUNCTION;
			}
			case "partialfunction" -> {
				return ModelicaClassType.PARTIAL_FUNCTION;
			}
			case "purefunction" -> {
				return ModelicaClassType.PURE_FUNCTION;
			}
			case "impurefunction" -> {
				return ModelicaClassType.IMPURE_FUNCTION;
			}
			case "connector" -> {
				return ModelicaClassType.CONNECTOR;
			}
			case "type" -> {
				return ModelicaClassType.TYPE;
			}
			case "block" -> {
				return ModelicaClassType.BLOCK;
			}
			case "partialblock" -> {
				return ModelicaClassType.PARTIAL_BLOCK;
			}
			case "package" -> {
				return ModelicaClassType.PACKAGE;
			}
			case "operator" -> {
				return ModelicaClassType.OPERATOR;
			}
			case "operatorrecord" -> {
				return ModelicaClassType.OPERATOR_RECORD;
			}
			case "operatorfunction" -> {
				return ModelicaClassType.OPERATOR_FUNCTION;
			}
			case "class" -> {
				return ModelicaClassType.CLASS;
			}
			case "partialclass" -> {
				return ModelicaClassType.PARTIAL_CLASS;
			}
			case "expandableconnector" -> {
				return ModelicaClassType.EXPANDABLE_CONNECTOR;
			}
			default -> throw new IllegalStateException("Unexpected value: " + classPrefix);
		}
	}
}
