package interpret.nodos.conditionals;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoComparison implements Expression {
    public enum Operator { EQ, NEQ, GT, LT, GE, LE }

    private final Expression left;
    private final Expression right;
    private final Operator operator;

    public NodoComparison(Expression left, Expression right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Object interpret(ContextMatch context) {
        Object leftValue = left.interpret(context);
        Object rightValue = right.interpret(context);

        if (leftValue instanceof Number && rightValue instanceof Number) {
            double leftNum = ((Number) leftValue).doubleValue();
            double rightNum = ((Number) rightValue).doubleValue();
            return switch (operator) {
                case EQ -> leftNum == rightNum;
                case NEQ -> leftNum != rightNum;
                case GT -> leftNum > rightNum;
                case LT -> leftNum < rightNum;
                case GE -> leftNum >= rightNum;
                case LE -> leftNum <= rightNum;
            };
        }

        if (leftValue instanceof java.util.ArrayList && rightValue instanceof java.util.ArrayList) {
            return switch (operator) {
                case EQ -> leftValue.equals(rightValue);
                case NEQ -> !leftValue.equals(rightValue);
                default -> throw new RuntimeException("Operador " + operator + " no permitido para listas");
            };
        }

        if (leftValue instanceof Boolean && rightValue instanceof Boolean) {
            boolean leftBool = (Boolean) leftValue;
            boolean rightBool = (Boolean) rightValue;
            return switch (operator) {
                case EQ -> leftBool == rightBool;
                case NEQ -> leftBool != rightBool;
                default -> throw new RuntimeException("Operador " + operator + " no permitido para booleanos");
            };
        }
        if (leftValue instanceof String && rightValue instanceof String) {
            return switch (operator) {
                case EQ -> leftValue.equals(rightValue);
                case NEQ -> !leftValue.equals(rightValue);
                default -> throw new RuntimeException("Operador " + operator + " no permitido para acciones");
            };
        }
        throw new RuntimeException("Tipos incompatibles para comparación: "
                + leftValue.getClass() + " y " + rightValue.getClass());
    }
}
