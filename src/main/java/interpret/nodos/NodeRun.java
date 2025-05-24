package interpret.nodos;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;
import java.util.List;

public class NodeRun implements Expression {
    private final List<String> matches;
    private final long seed;

    public NodeRun(List<String> matches, long seed) {
        if (matches == null || matches.isEmpty()) {
            throw new RuntimeException("Debe haber al menos un match en el run.");
        }
        if (seed <= 0) {
            throw new RuntimeException("La semilla en el run debe ser un entero positivo.");
        }
        this.matches = matches;
        this.seed = seed;
    }

    @Override
    public Object interpret(ContextMatch context) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ejecución de Run con seed: ").append(seed).append("\n");
        for (String matchId : matches) {
            NodoMatch match = context.getMatch(matchId);
            if (match == null) {
                throw new RuntimeException("El match '" + matchId + "' no está definido.");
            }

            match.setSeed(seed);
            sb.append(match.interpret(context)).append("\n");
        }
        return sb.toString();
    }
}