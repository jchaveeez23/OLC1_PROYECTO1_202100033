package interpret;

import interpret.contexts.ContextMatch;
import interpret.match.Scoring;
import interpret.nodos.NodoMain;
import interpret.nodos.NodoMatch;
import interpret.nodos.NodeRun;
import interpret.nodos.NodoRule;
import interpret.nodos.NodoStrategy;
import interpret.nodos.types.NodoAction;

import java.util.ArrayList;
import java.util.List;

public class MainTestMain {
    public static void main(String[] args) {
        // --- Definir estrategias simples ---
        List<NodoRule> rules1 = new ArrayList<>();
        // Regla else: retorna "C"
        rules1.add(new NodoRule(null, new NodoAction("C")));
        NodoStrategy strategy1 = new NodoStrategy("TitForTat", new NodoAction("C"), rules1);

        List<NodoRule> rules2 = new ArrayList<>();
        // Regla else: retorna "D"
        rules2.add(new NodoRule(null, new NodoAction("D")));
        NodoStrategy strategy2 = new NodoStrategy("AlwaysDefect", new NodoAction("D"), rules2);

        // --- Configurar la partida (match) ---
        Scoring scoring = new Scoring(3, 1, 5, 0);
        int rounds = 10;
        long matchSeed = 12345;
        NodoMatch match1 = new NodoMatch("Match1", "TitForTat", "AlwaysDefect", rounds, scoring);
        match1.setSeed(matchSeed);

        // --- Configurar el run ---
        List<NodoMatch> matchesRun = new ArrayList<>();
        matchesRun.add(match1);

        List<String> matchesIds = new ArrayList<>();
        matchesIds.add("Match1");

        long runSeed = 12345;
        NodeRun run1 = new NodeRun(matchesIds, runSeed);

        // --- Configurar el main ---
        List<NodeRun> runs = new ArrayList<>();
        runs.add(run1);
        NodoMain mainNode = new NodoMain(runs);

        // Crear un contexto global dummy (no se utiliza para los matches, ya que estos crean sus propios contextos)
        ContextMatch globalContext = new ContextMatch(rounds, null);

        // Ejecutar el nodo main
        String output = (String) mainNode.interpret(globalContext);
        System.out.println(output);

        // Ejemplo de validación: Descomenta el siguiente bloque para ver una validación fallida.
        /*
        try {
            NodeRun runErroneo = new NodeRun(new ArrayList<>(), -1);
        } catch (RuntimeException e) {
            System.err.println("Error de validación: " + e.getMessage());
        }
        */
    }
}
