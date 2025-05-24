package interpret;

import interpret.match.Scoring;
import interpret.nodos.NodoRule;
import interpret.nodos.NodoStrategy;
import interpret.nodos.NodoMatch;
import interpret.nodos.types.NodoAction;

import java.util.ArrayList;
import java.util.List;

public class TestMatch {
    public static void main(String[] args) {
        List<NodoRule> rules1 = new ArrayList<>();
        rules1.add(new NodoRule(null, new NodoAction("C")));
        NodoStrategy strategy1 = new NodoStrategy("TitForTat", new NodoAction("C"), rules1);

        List<NodoRule> rules2 = new ArrayList<>();
        rules2.add(new NodoRule(null, new NodoAction("D")));
        NodoStrategy strategy2 = new NodoStrategy("AlwaysDefect", new NodoAction("D"), rules2);

        Scoring scoring = new Scoring(3, 1, 5, 0);

        long seed = 12345;

        NodoMatch match = new NodoMatch("Match1", "TitForTat", "AlwaysDefect", 10, scoring);
        match.setSeed(seed);

        String matchResult = (String) match.interpret(null);
        System.out.println(matchResult);
    }
}
