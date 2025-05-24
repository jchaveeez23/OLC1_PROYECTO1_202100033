package interpret.contracts;

import interpret.contexts.ContextMatch;

public interface Expression {
    Object interpret(ContextMatch context);
}