package interpret.nodos.types;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoAction implements Expression {
    private final String action; // "C" o "D"

    public NodoAction(String action) {
        this.action = action;
    }

    @Override
    public Object interpret(ContextMatch context) {
        return action;
    }
}
