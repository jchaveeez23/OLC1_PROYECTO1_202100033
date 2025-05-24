package interpret.contexts;

import java.util.ArrayList;
import java.util.List;

public class Context {
    private int roundNumber;
    private final int totalRounds;
    private final List<String> opponentHistory;
    private final List<String> selfHistory;

    public Context(int totalRounds) {
        this.totalRounds = totalRounds;
        this.roundNumber = 0;
        this.opponentHistory = new ArrayList<>();
        this.selfHistory = new ArrayList<>();
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
}
