package interpret.nodos;

import interpret.contexts.ContextMatch;
import interpret.contracts.Expression;

import java.util.List;

public class NodoProgram implements Expression {
    private final Expression mainSection;
    private final List<Expression> strategies;
    private final List<Expression> matches;

    public NodoProgram(List<Expression> strategies, List<Expression> matches, Expression mainSection) {
        this.mainSection = mainSection;
        this.strategies = strategies;
        this.matches = matches;
    }

    @Override
    public Object interpret(ContextMatch context) {
        context.setMatches(this.matches);
        context.setStrategies(this.strategies);

        return mainSection.interpret(context);
    }

    public List<Expression> getStrategies() {
        return strategies;
    }

    public List<Expression> getMatches() {
        return matches;
    }

    public Expression getMainSection() {
        return mainSection;
    }
}
