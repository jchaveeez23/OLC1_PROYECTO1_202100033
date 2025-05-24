package interpret.nodos;

import interpret.contracts.Expression;
import interpret.contexts.ContextMatch;
import interpret.match.Scoring;
import interpret.random.DeterministicRandomGenerator;
import interpret.random.RandomGenerator;
import java.util.List;

public class NodoMatch implements Expression {
    private final String matchName;
    private String strategy1 = "";
    private String strategy2 = "";
    private final int rounds;
    private final Scoring scoring;
    private long seed;

    public NodoMatch(String matchName, String strategy1, String strategy2, int rounds, Scoring scoring) {
        if (rounds <= 0) {
            throw new RuntimeException("El número de rondas debe ser mayor a 0.");
        }
        if (strategy1 == null || strategy2 == null) {
            throw new RuntimeException("Ambas estrategias deben estar definidas para el match.");
        }
        if (scoring == null) {
            throw new RuntimeException("La configuración de puntuación no puede ser nula.");
        }
        this.matchName = matchName;
        this.rounds = rounds;
        this.scoring = scoring;
        this.seed = 1;
        this.strategy1 = strategy1;
        this.strategy2 = strategy2;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public Object interpret(ContextMatch context) {
        if (seed <= 0) {
            throw new RuntimeException("La semilla debe ser un entero positivo.");
        }

        NodoStrategy strategy1 = context.getStrategy(this.strategy1);
        NodoStrategy strategy2 = context.getStrategy(this.strategy2);
        // validar si las estrategias son nulas
        if (strategy1 == null) {
            throw new RuntimeException("La estrategia '" + this.strategy1 + "' no está definida.");
        }

        if (strategy2 == null) {
            throw new RuntimeException("La estrategia '" + this.strategy2 + "' no está definida.");
        }

        RandomGenerator generator1 = DeterministicRandomGenerator.create(seed);
        RandomGenerator generator2 = DeterministicRandomGenerator.create(seed);
        ContextMatch contextPlayer1 = new ContextMatch(rounds, generator1);
        ContextMatch contextPlayer2 = new ContextMatch(rounds, generator2);

        int score1 = 0;
        int score2 = 0;
        StringBuilder logRounds = new StringBuilder();

        for (int r = 0; r < rounds; r++) {
            contextPlayer1.incrementRound();
            contextPlayer2.incrementRound();
            String action1 = (String) strategy1.interpret(contextPlayer1);
            String action2 = (String) strategy2.interpret(contextPlayer2);

            contextPlayer1.addSelfAction(action1);
            contextPlayer1.addOpponentAction(action2);
            contextPlayer2.addSelfAction(action2);
            contextPlayer2.addOpponentAction(action1);

            int roundScore1 = 0;
            int roundScore2 = 0;
            if (action1.equals("C") && action2.equals("C")) {
                roundScore1 = scoring.getMutualCooperation();
                roundScore2 = scoring.getMutualCooperation();
            } else if (action1.equals("D") && action2.equals("D")) {
                roundScore1 = scoring.getMutualDefection();
                roundScore2 = scoring.getMutualDefection();
            } else if (action1.equals("C") && action2.equals("D")) {
                roundScore1 = scoring.getBetrayalPunishment();
                roundScore2 = scoring.getBetrayalReward();
            } else if (action1.equals("D") && action2.equals("C")) {
                roundScore1 = scoring.getBetrayalReward();
                roundScore2 = scoring.getBetrayalPunishment();
            }
            score1 += roundScore1;
            score2 += roundScore2;

            logRounds.append("Ronda ").append(r + 1).append(": ");
            logRounds.append(strategy1.getName()).append("=").append(action1).append(", ");
            logRounds.append(strategy2.getName()).append("=").append(action2);
            logRounds.append(" (").append(roundScore1).append("-").append(roundScore2).append(")\n");
        }

        int countC1 = countOccurrences(contextPlayer1.getSelfHistory(), "C");
        int countD1 = countOccurrences(contextPlayer1.getSelfHistory(), "D");
        double percentC1 = (countC1 * 100.0) / rounds;
        double percentD1 = (countD1 * 100.0) / rounds;

        int countC2 = countOccurrences(contextPlayer2.getSelfHistory(), "C");
        int countD2 = countOccurrences(contextPlayer2.getSelfHistory(), "D");
        double percentC2 = (countC2 * 100.0) / rounds;
        double percentD2 = (countD2 * 100.0) / rounds;

        StringBuilder output = new StringBuilder();
        output.append("=== Partida ===\n");
        output.append("Configuración:\n");
        output.append("Rondas: ").append(rounds).append("\n");
        output.append("Estrategias: ").append(strategy1.getName()).append(" vs ").append(strategy2.getName()).append("\n");
        output.append("Scoring:\n");
        output.append("- Cooperación mutua: ").append(scoring.getMutualCooperation()).append("\n");
        output.append("- Defección mutua: ").append(scoring.getMutualDefection()).append("\n");
        output.append("- Traición: ").append(scoring.getBetrayalReward()).append("/")
                .append(scoring.getBetrayalPunishment()).append(" (traidor/traicionado)\n");
        output.append("Desarrollo:\n");
        output.append(logRounds);
        output.append("Resumen:\n");
        output.append(strategy1.getName()).append(":\n");
        output.append("- Puntuación final: ").append(score1).append("\n");
        output.append("- Cooperaciones: ").append(String.format("%.0f", percentC1)).append("%\n");
        output.append("- Defecciones: ").append(String.format("%.0f", percentD1)).append("%\n");
        output.append(strategy2.getName()).append(":\n");
        output.append("- Puntuación final: ").append(score2).append("\n");
        output.append("- Cooperaciones: ").append(String.format("%.0f", percentC2)).append("%\n");
        output.append("- Defecciones: ").append(String.format("%.0f", percentD2)).append("%\n");

        return output.toString();
    }

    private int countOccurrences(List<String> actions, String value) {
        int count = 0;
        for (String action : actions) {
            if (action.equals(value)) {
                count++;
            }
        }
        return count;
    }

    public String getName() {
        return matchName;
    }
}
