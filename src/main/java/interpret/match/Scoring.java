package interpret.match;

public class Scoring {
    private int mutualCooperation;
    private int mutualDefection;
    private int betrayalReward;
    private int betrayalPunishment;

    public Scoring(int mutualCooperation, int mutualDefection, int betrayalReward, int betrayalPunishment) {
        this.mutualCooperation = mutualCooperation;
        this.mutualDefection = mutualDefection;
        this.betrayalReward = betrayalReward;
        this.betrayalPunishment = betrayalPunishment;
    }

    public int getMutualCooperation() {
        return mutualCooperation;
    }

    public int getMutualDefection() {
        return mutualDefection;
    }

    public int getBetrayalReward() {
        return betrayalReward;
    }

    public int getBetrayalPunishment() {
        return betrayalPunishment;
    }
}
