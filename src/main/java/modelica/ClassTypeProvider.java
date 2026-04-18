package modelica;

public class ClassTypeProvider {
	public static ClassType getClassType(String classPrefix) throws IllegalStateException {
		switch (classPrefix){
			case "model" -> {
				return ClassType.MODEL;
			}
			case "partialmodel" -> {
				return ClassType.PARTIAL_MODEL;
			}
			case "record" -> {
				return ClassType.RECORD;
			}
			case "partialrecord" -> {
				return ClassType.PARTIAL_RECORD;
			}
			case "function" -> {
				return ClassType.FUNCTION;
			}
			case "partialfunction" -> {
				return ClassType.PARTIAL_FUNCTION;
			}
			case "purefunction" -> {
				return ClassType.PURE_FUNCTION;
			}
			case "impurefunction" -> {
				return ClassType.IMPURE_FUNCTION;
			}
			case "connector" -> {
				return ClassType.CONNECTOR;
			}
			case "type" -> {
				return ClassType.TYPE;
			}
			case "block" -> {
				return ClassType.BLOCK;
			}
			case "partialblock" -> {
				return ClassType.PARTIAL_BLOCK;
			}
			case "package" -> {
				return ClassType.PACKAGE;
			}
			case "operator" -> {
				return ClassType.OPERATOR;
			}
			case "operatorrecord" -> {
				return ClassType.OPERATOR_RECORD;
			}
			case "operatorfunction" -> {
				return ClassType.OPERATOR_FUNCTION;
			}
			case "class" -> {
				return ClassType.CLASS;
			}
			case "partialclass" -> {
				return ClassType.PARTIAL_CLASS;
			}
			case "expandableconnector" -> {
				return ClassType.EXPANDABLE_CONNECTOR;
			}
			default -> throw new IllegalStateException("Unexpected value: " + classPrefix);
		}
	}
}
