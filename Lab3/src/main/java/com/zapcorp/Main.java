package com.zapcorp;

public class Main {

    private static final boolean ITERATIVE = true;

    private static final int N = 10;
    private static final int K = 3;
    private static final float P = 0.5f;

    private static final int ITERATIONS = 100_000;

    private static final int EXECUTION_TIME_SECONDS = 10;
    private static final int ITERATION_DELAY_MILLIS = 10;

    public static void main(final String... args) {
        if (ITERATIVE) {
            new BrownianMotionEmulation(N, K, P, ITERATIONS)
                    .run();
        } else {
            new BrownianMotionEmulation(N, K, P, EXECUTION_TIME_SECONDS * 1000, ITERATION_DELAY_MILLIS)
                    .run();
        }
    }
}
