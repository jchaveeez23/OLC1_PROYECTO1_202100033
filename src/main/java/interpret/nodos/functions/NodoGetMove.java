package interpret.nodos.functions;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;
import java.util.List;

public class NodoGetMove implements Expression {
    private final Expression history;
    private final Expression indexExpr;

    public NodoGetMove(Expression history, Expression indexExpr) {
        this.history = history;
        this.indexExpr = indexExpr;
    }

    @Override
    public Object interpret(ContextMatch context) {
        Object historyVal = history.interpret(context);
        Object indexVal = indexExpr.interpret(context);

        if (!(historyVal instanceof List<?> list)) {
            throw new RuntimeException("El primer argumento de get_move debe ser una lista.");
        }
        if (!(indexVal instanceof Number)) {
            throw new RuntimeException("El segundo argumento de get_move debe ser un entero.");
        }

        int index = ((Number) indexVal).intValue();

        if (index < 0 || index >= list.size()) {
            throw new RuntimeException("Índice fuera de rango en get_move.");
        }

        return list.get(index);
    }
}
