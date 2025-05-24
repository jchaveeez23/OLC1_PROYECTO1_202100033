package interpret.nodos.types;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoFloat implements Expression {
    private final double value;

    public NodoFloat(double value) {
        this.value = value;
    }

    @Override
    public Object interpret(ContextMatch context) {
        return value;
    }
}
