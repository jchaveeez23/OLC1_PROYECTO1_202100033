package interpret.nodos.conditionals;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoNot implements Expression {
    private final Expression expr;

    public NodoNot(Expression expr) {
        this.expr = expr;
    }

    @Override
    public Object interpret(ContextMatch context) {
        Object value = expr.interpret(context);
        if (!(value instanceof Boolean)) {
            throw new RuntimeException("El operador NOT requiere un valor booleano.");
        }
        return !((Boolean) value);
    }
}
