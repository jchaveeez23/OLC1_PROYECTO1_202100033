package interpret.nodos;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoRule implements Expression {
    private final Expression condition;
    private final Expression action;

    public NodoRule(Expression condition, Expression action) {
        this.condition = condition;
        this.action = action;
    }

    @Override
    public Object interpret(ContextMatch context) {
        if (condition != null) {
            Object condValue = condition.interpret(context);

            if (!(condValue instanceof Boolean)) {
                throw new RuntimeException("La condición de la regla debe evaluar a un valor booleano.");
            }
            if ((Boolean) condValue) {
                return action.interpret(context);
            }
        } else {
            return action.interpret(context);
        }
        return null;
    }

    /**
     * Indica si la regla es de tipo 'else' (sin condición).
     * @return true si no hay condición; false en caso contrario.
     */
    public boolean isElseRule() {
        return condition == null;
    }
}
