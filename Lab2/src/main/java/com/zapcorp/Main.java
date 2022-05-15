package com.zapcorp;

import com.zapcorp.util.RandUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
public class Main {

    private static final int TWO = 2;

    private static final int LIST_SIZE = (int) Math.pow(TWO, 19);
    private static final double ELEMENTS_ORIGIN = Math.pow(TWO, 1);
    private static final double ELEMENTS_BOUND = Math.pow(TWO, 23);

    private static final int THREADS_NUM_POWER = (int) Math.pow(TWO, 3);
    private static final int MEASUREMENT_COUNT = (int) Math.pow(TWO, 3);
    private static final String RESULT_FILE_PATH = "./res/measurements.json";

    private static final Function<Double, Double> FUNCTION =
            (x) -> Math.sqrt(Math.pow(TWO, 7) * Math.pow(x, -Math.sqrt(x)) / Math.pow(TWO, 6)) + x / Math.pow(TWO, 2);


    public static void main(final String... args) throws IOException {
        clearFiles();

        log.info("Transformations measurement started..");
        final var start = System.currentTimeMillis();

        new ConcurrentListTransformer(
                THREADS_NUM_POWER,
                RandUtil.generateList(LIST_SIZE, ELEMENTS_ORIGIN, ELEMENTS_BOUND),
                FUNCTION,
                MEASUREMENT_COUNT,
                RESULT_FILE_PATH
        ).runTransformations();

        log.info("Transformations measurement finished successfully. It took {} seconds.",
                (System.currentTimeMillis() - start)/ 1000);
    }

    private static void clearFiles() throws IOException {
        final var loader = Thread.currentThread().getContextClassLoader();

        Files.newBufferedWriter(
                Path.of(Objects.requireNonNull(loader.getResource(RESULT_FILE_PATH)).getPath()),
                StandardOpenOption.TRUNCATE_EXISTING).close();
    }
}