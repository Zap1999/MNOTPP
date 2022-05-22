package com.zapcorp;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {

    private static final boolean ITERATIVE = true;

    private static final int N = 10;
    private static final int K = 128;
    private static final float P = 0.9f;

    private static final int ITERATIONS = 1_000_000;

    private static final int EXECUTION_TIME_SECONDS = 10;
    private static final int ITERATION_DELAY_MILLIS = 10;

    public static void main(final String... args) {
        if (ITERATIVE) {
            final var startTime = System.nanoTime();
            new BrownianMotionEmulation(N, K, P, ITERATIONS)
                    .run();
            final var endTime = System.nanoTime();
            log.info("Execution for {} iterations with {} particles and {} right move probability took {} ns.",
                    ITERATIONS, K, P, endTime - startTime);
        } else {
            new BrownianMotionEmulation(N, K, P, EXECUTION_TIME_SECONDS * 1000, ITERATION_DELAY_MILLIS)
                    .run();
        }
    }
}
