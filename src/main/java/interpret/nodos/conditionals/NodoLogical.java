package interpret.nodos.conditionals;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoLogical implements Expression {
    public enum Operator { AND, OR }

    private final Expression left;
    private final Expression right;
    private final Operator operator;

    public NodoLogical(Expression left, Expression right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Object interpret(ContextMatch context) {
        Object leftValue = left.interpret(context);
        if (!(leftValue instanceof Boolean)) {
            throw new RuntimeException("El operando izquierdo no es booleano en operación lógica.");
        }
        boolean lv = (Boolean) leftValue;

        if (operator == Operator.AND) {
            if (!lv) {
                return false;
            }
            Object rightValue = right.interpret(context);
            if (!(rightValue instanceof Boolean)) {
                throw new RuntimeException("El operando derecho no es booleano en operación lógica.");
            }
            return (Boolean) rightValue;
        } else {
            if (lv) {
                return true;
            }
            Object rightValue = right.interpret(context);
            if (!(rightValue instanceof Boolean)) {
                throw new RuntimeException("El operando derecho no es booleano en operación lógica.");
            }
            return (Boolean) rightValue;
        }
    }
}
