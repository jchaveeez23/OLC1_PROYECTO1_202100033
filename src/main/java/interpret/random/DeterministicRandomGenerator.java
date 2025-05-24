package interpret.random;

import java.util.Random;


public class DeterministicRandomGenerator implements RandomGenerator {
    private final long originalSeed;
    private final Random random;

    private DeterministicRandomGenerator(long seed) {
        this.originalSeed = seed;
        this.random = new Random(seed);
    }

    public static DeterministicRandomGenerator create(long seed) {
        return new DeterministicRandomGenerator(seed);
    }
    @Override
    public double nextDouble() {
        return random.nextDouble();
    }
}