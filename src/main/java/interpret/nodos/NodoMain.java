package interpret.nodos;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;
import java.util.List;

public class NodoMain implements Expression {
    private final List<NodeRun> runs;

    public NodoMain(List<NodeRun> runs) {
        if (runs == null || runs.isEmpty()) {
            throw new RuntimeException("Debe haber al menos un run en el main.");
        }
        this.runs = runs;
    }

    @Override
    public Object interpret(ContextMatch context) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Punto de Entrada =====\n");
        for (NodeRun run : runs) {
            sb.append(run.interpret(context)).append("\n");
        }
        return sb.toString();
    }
}
