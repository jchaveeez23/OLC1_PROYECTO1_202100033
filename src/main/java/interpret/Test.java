package interpret;

import interpret.contexts.ContextMatch;
import interpret.nodos.NodoRule;
import interpret.nodos.NodoStrategy;
import interpret.random.DeterministicRandomGenerator;
import interpret.random.RandomGenerator;
import interpret.contracts.Expression;
import interpret.nodos.functions.NodoGetLastNMoves;
import interpret.nodos.functions.NodoGetMove;
import interpret.nodos.functions.NodoGetMovesCount;
import interpret.nodos.functions.NodoLastMove;
import interpret.nodos.states.*;
import interpret.nodos.types.NodoAction;
import interpret.nodos.types.NodoBoolean;
import interpret.nodos.types.NodoInt;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        RandomGenerator generator = DeterministicRandomGenerator.create(42);
        ContextMatch context = new ContextMatch(10, generator);

        context.addOpponentAction("C");
        context.addOpponentAction("D");
        context.addSelfAction("D");
        context.addSelfAction("C");


        Expression nodoRound = new NodoRoundNumber();
        System.out.println("Round number: " + nodoRound.interpret(context));

        Expression nodoTotalRounds = new NodoTotalRounds();
        System.out.println("Total rounds: " + nodoTotalRounds.interpret(context));

        Expression nodoOpponentHistory = new NodoOpponentHistory();
        System.out.println("Opponent History: " + nodoOpponentHistory.interpret(context));

        Expression nodoSelfHistory = new NodoSelfHistory();
        System.out.println("Self History: " + nodoSelfHistory.interpret(context));

        Expression nodoRandom = new NodoRandom();
        System.out.println("Random value: " + nodoRandom.interpret(context));

        context.incrementRound();
        System.out.println("\nDespués de incrementar la ronda:");
        System.out.println("Round number: " + new NodoRoundNumber().interpret(context));

        Expression trueCondition = new NodoBoolean(true);
        Expression actionC = new NodoAction("C");
        NodoRule rule = new NodoRule(trueCondition, actionC);
        List<NodoRule> rules = new ArrayList<>();
        rules.add(rule);
        NodoStrategy strategy = new NodoStrategy("TestStrategy", new NodoAction("D"), rules);

        System.out.println("\nEjecución de la estrategia '" + strategy.getName() + "' en la ronda " +
                context.getRoundNumber() + ": " + strategy.interpret(context));

        Expression selfHistoryExpr = new NodoSelfHistory();
        Expression indexExpr = new NodoInt(1); // índice 1
        NodoGetMove getMoveNode = new NodoGetMove(selfHistoryExpr, indexExpr);
        System.out.println("\nget_move (self_history, 1): " + getMoveNode.interpret(context));

        NodoLastMove lastMoveNode = new NodoLastMove(new NodoOpponentHistory());
        System.out.println("last_move (opponent_history): " + lastMoveNode.interpret(context));

        NodoGetMovesCount getMovesCountNode = new NodoGetMovesCount(new NodoOpponentHistory(), new NodoAction("C"));
        System.out.println("get_moves_count (opponent_history, 'C'): " + getMovesCountNode.interpret(context));

        NodoGetLastNMoves getLastNMovesNode = new NodoGetLastNMoves(new NodoSelfHistory(), new NodoInt(1));
        System.out.println("get_last_n_moves (self_history, 1): " + getLastNMovesNode.interpret(context));
    }
}
