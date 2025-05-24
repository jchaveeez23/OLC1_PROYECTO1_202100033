package interpret.nodos.types;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoInt implements Expression {
    private final int value;

    public NodoInt(int value) {
        this.value = value;
    }

    @Override
    public Object interpret(ContextMatch context) {
        return value;
    }
}
