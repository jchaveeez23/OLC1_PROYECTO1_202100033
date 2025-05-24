package interpret.nodos.functions;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;
import java.util.ArrayList;
import java.util.List;

public class NodoGetLastNMoves implements Expression {
    private final Expression history;
    private final Expression nExpr;

    public NodoGetLastNMoves(Expression history, Expression nExpr) {
        this.history = history;
        this.nExpr = nExpr;
    }

    @Override
    public Object interpret(ContextMatch context) {
        Object historyVal = history.interpret(context);
        Object nVal = nExpr.interpret(context);

        if (!(historyVal instanceof List<?> list)) {
            throw new RuntimeException("El primer argumento de get_last_n_moves debe ser una lista.");
        }
        if (!(nVal instanceof Number)) {
            throw new RuntimeException("El segundo argumento de get_last_n_moves debe ser un entero.");
        }

        int n = ((Number) nVal).intValue();

        if (n <= 0 || n > list.size()) {
            throw new RuntimeException("El valor de n en get_last_n_moves es inválido.");
        }

        List<Object> result = new ArrayList<>();

        for (int i = list.size() - n; i < list.size(); i++) {
            result.add(list.get(i));
        }
        return result;
    }
}
