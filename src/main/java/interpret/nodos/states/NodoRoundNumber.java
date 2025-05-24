package interpret.nodos.states;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;

public class NodoRoundNumber implements Expression {
    @Override
    public Object interpret(ContextMatch context) {
        return context.getRoundNumber();
    }
}
