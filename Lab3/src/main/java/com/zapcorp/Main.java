package com.zapcorp;

public class Main {

    private static final int N = 10;
    private static final int K = 3;
    private static final float P = 0.5f;

    private static final int ITERATIONS = 100_000;

    private static final int EXECUTION_TIME_SECONDS = 10;
    private static final int ITERATION_DELAY_MILLIS = 100;

    public static void main(final String... args) {
        final var iterativeStrategy = new IterativeParticleStrategy(ITERATIONS);
        new BrownianMotionEmulation(N, K, P, iterativeStrategy)
                .run();
    }
}
