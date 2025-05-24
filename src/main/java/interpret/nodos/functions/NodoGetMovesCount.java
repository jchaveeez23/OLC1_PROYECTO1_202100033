package interpret.nodos.functions;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;
import java.util.List;

public class NodoGetMovesCount implements Expression {
    private final Expression history;
    private final Expression actionExpr;

    public NodoGetMovesCount(Expression history, Expression actionExpr) {
        this.history = history;
        this.actionExpr = actionExpr;
    }

    @Override
    public Object interpret(ContextMatch context) {
        Object historyVal = history.interpret(context);
        Object actionVal = actionExpr.interpret(context);

        if (!(historyVal instanceof List<?> list)) {
            throw new RuntimeException("El primer argumento de get_moves_count debe ser una lista.");
        }
        if (!(actionVal instanceof String action)) {
            throw new RuntimeException("El segundo argumento de get_moves_count debe ser una acción (cadena).");
        }

        int count = 0;
        for (Object item : list) {
            if (action.equals(item)) {
                count++;
            }
        }
        return count;
    }
}
