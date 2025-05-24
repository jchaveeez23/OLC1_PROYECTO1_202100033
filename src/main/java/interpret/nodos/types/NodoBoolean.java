package interpret.nodos.types;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoBoolean implements Expression {
    private final boolean value;

    public NodoBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public Object interpret(ContextMatch context) {
        return value;
    }
}
