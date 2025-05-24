package interpret.nodos.functions;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;
import java.util.List;

public class NodoLastMove implements Expression {
    private final Expression history;

    public NodoLastMove(Expression history) {
        this.history = history;
    }

    @Override
    public Object interpret(ContextMatch context) {
        Object historyVal = history.interpret(context);
        if (!(historyVal instanceof List<?> list)) {
            throw new RuntimeException("El argumento de last_move debe ser una lista.");
        }
        if (list.isEmpty()) {
            throw new RuntimeException("La lista de movimientos está vacía en last_move.");
        }
        return list.getLast();
    }
}
