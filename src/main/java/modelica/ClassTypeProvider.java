package modelica;

public class ClassTypeProvider {
	public static ClassType getClassType(String classPrefix) throws IllegalStateException {
		switch (classPrefix){
			case "model" -> {
				return ClassType.MODEL;
			}
			case "record" -> {
				return ClassType.RECORD;
			}
			case "function" -> {
				return ClassType.FUNCTION;
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
			case "package" -> {
				return ClassType.PACKAGE;
			}
			case "operator" -> {
				return ClassType.OPERATOR;
			}
			case "operator record" -> {
				return ClassType.OPERATOR_RECORD;
			}
			case "operator function" -> {
				return ClassType.OPERATOR_FUNCTION;
			}
			case "expandable connector" -> {
				return ClassType.EXPANDABLE_CONNECTOR;
			}
			default -> throw new IllegalStateException("Unexpected value: " + classPrefix);
		}
	}
}
