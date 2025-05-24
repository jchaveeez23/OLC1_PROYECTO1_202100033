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

public class MainTestADefectvsACoop {
    public static void main(String[] args) {
        // --- Definir la estrategia AlwaysDefect ---
        List<NodoRule> rulesAlwaysDefect = new ArrayList<>();
        // Regla else: retorna "D"
        rulesAlwaysDefect.add(new NodoRule(null, new NodoAction("D")));
        NodoStrategy alwaysDefect = new NodoStrategy("AlwaysDefect", new NodoAction("D"), rulesAlwaysDefect);

        // --- Definir la estrategia AlwaysCooperate ---
        List<NodoRule> rulesAlwaysCooperate = new ArrayList<>();
        // Regla else: retorna "C"
        rulesAlwaysCooperate.add(new NodoRule(null, new NodoAction("C")));
        NodoStrategy alwaysCooperate = new NodoStrategy("AlwaysCooperate", new NodoAction("C"), rulesAlwaysCooperate);

        // --- Configurar el match "ADefectvsACoop" ---
        // Scoring:
        // - Cooperación mutua: 3 puntos
        // - Defección mutua: 1 punto
        // - Recompensa por traición: 5 puntos
        // - Castigo por traición: 0 puntos
        Scoring scoring = new Scoring(3, 1, 5, 0);
        int rounds = 100;
        long matchSeed = 42; // Semilla para el match (proveniente de run)
        NodoMatch match = new NodoMatch("ADefectvsACoop", "alwaysDefect", "alwaysCooperate", rounds, scoring);
        match.setSeed(matchSeed);

        // --- Configurar el run ---
        List<NodoMatch> matches = new ArrayList<>();
        matches.add(match);

        List<String> matchesIds = new ArrayList<>();
        matchesIds.add("Match1");

        long runSeed = 42; // Semilla para el run
        NodeRun run = new NodeRun(matchesIds, runSeed);

        // --- Configurar el main ---
        List<NodeRun> runs = new ArrayList<>();
        runs.add(run);
        NodoMain mainNode = new NodoMain(runs);

        // Crear un contexto global dummy (no se utiliza realmente, pues cada match crea sus propios contextos)
        ContextMatch globalContext = new ContextMatch(rounds, null);

        // Ejecutar el nodo main y mostrar la salida
        String output = (String) mainNode.interpret(globalContext);
        System.out.println(output);
    }
}
