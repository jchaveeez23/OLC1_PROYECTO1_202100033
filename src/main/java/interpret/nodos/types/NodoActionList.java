package interpret.nodos.types;

import interpret.contexts.ContextMatch;
import interpret.contracts.Expression;

import java.util.ArrayList;
import java.util.List;

public class NodoActionList implements Expression {
    private final List<Expression> actions;
    private final List<Object> result;

    public NodoActionList(
            List<Expression> actions
    ) {
        this.actions = actions;
        this.result = new ArrayList<>();
    }

    @Override
    public Object interpret(ContextMatch context) {
        for (Expression action : actions) {
            result.add(action.interpret(context));
        }

        return result;
    }
}
