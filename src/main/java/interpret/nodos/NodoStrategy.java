package interpret.nodos;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;
import java.util.List;

public class NodoStrategy implements Expression {

    private final String name;
    private final Expression initialAction;
    private final List<NodoRule> rules;

    public NodoStrategy(String name, Expression initialAction, List<NodoRule> rules) {
        this.name = name;
        this.initialAction = initialAction;
        this.rules = rules;
        validateStrategy();
    }


    private void validateStrategy() {
        boolean hasElse = false;
        for (NodoRule rule : rules) {
            if (rule.isElseRule()) {
                hasElse = true;
                break;
            }
        }
        if (!hasElse) {
            System.err.println("Advertencia: La estrategia '" + name
                    + "' no tiene una regla else. Se recomienda definir una regla por defecto.");
        }
    }

    @Override
    public Object interpret(ContextMatch context) {
        if (context.getRoundNumber() == 0) {
            return initialAction.interpret(context);
        } else {
            for (NodoRule rule : rules) {
                Object result = rule.interpret(context);
                if (result != null) {
                    return result;
                }
            }
            throw new RuntimeException("En la estrategia '" + name
                    + "', ninguna regla se cumplió y no se definió un valor por defecto.");
        }
    }

    public String getName() {
        return name;
    }
}
