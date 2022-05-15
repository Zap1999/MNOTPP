package com.zapcorp;

import com.zapcorp.util.RandUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class Main {

    private static final int LIST_SIZE = 500_000;
    private static final double ELEMENTS_ORIGIN = 2.0;
    private static final double ELEMENTS_BOUND = 10_000_000.0;

    private static final int THREADS_NUM_POWER = 8;
    private static final int MEASUREMENT_COUNT = 8;
    private static final String RESULT_FILE_PATH = "./res/measurements.json";

    private static final Function<Integer, Double> FUNCTION =
            (x) -> Math.sqrt(123 * Math.pow(x, -Math.sqrt(x)) / 68) + x / 3;


    public static void main(final String... args) {
        final var list = RandUtil.generateList(LIST_SIZE, ELEMENTS_ORIGIN, ELEMENTS_BOUND);

        new OpExecutor(THREADS_NUM_POWER, FUNCTION, MEASUREMENT_COUNT, RESULT_FILE_PATH)
                .runOp();
        log.info("");
    }
}