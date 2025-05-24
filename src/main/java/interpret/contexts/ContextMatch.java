package interpret.contexts;

import interpret.contracts.Expression;
import interpret.nodos.NodoMatch;
import interpret.nodos.NodoStrategy;
import interpret.random.RandomGenerator;
import java.util.ArrayList;
import java.util.List;

public class ContextMatch {
    private int roundNumber;
    private final int totalRounds;
    private final List<String> opponentHistory;
    private final List<String> selfHistory;
    private final RandomGenerator randomGenerator;
    private List<Expression> strategies = new ArrayList<>();
    private List<Expression> matches = new ArrayList<>();

    public ContextMatch(int totalRounds, RandomGenerator randomGenerator) {
        this.totalRounds = totalRounds;
        this.roundNumber = -1;
        this.opponentHistory = new ArrayList<>();
        this.selfHistory = new ArrayList<>();
        this.randomGenerator = randomGenerator;
    }

    public void setStrategies(List<Expression> strategies) {
        this.strategies = strategies;
    }

    public void setMatches(List<Expression> matches) {
        this.matches = matches;
    }

    public List<Expression> getStrategies() {
        return strategies;
    }

    public List<Expression> getMatches() {
        return matches;
    }

    public NodoMatch getMatch(String name) {
        return (NodoMatch) matches.stream().filter(m -> ((NodoMatch) m).getName().equals(name)).findFirst().orElse(null);
    }

    public NodoStrategy getStrategy(String name) {
        return (NodoStrategy) strategies.stream().filter(s -> ((NodoStrategy) s).getName().equals(name)).findFirst().orElse(null);
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void incrementRound() {
        roundNumber++;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public List<String> getOpponentHistory() {
        return opponentHistory;
    }

    public List<String> getSelfHistory() {
        return selfHistory;
    }

    public void addOpponentAction(String action) {
        opponentHistory.add(action);
    }

    public void addSelfAction(String action) {
        selfHistory.add(action);
    }

    public double getRandomValue() {
        return randomGenerator.nextDouble();
    }
}
