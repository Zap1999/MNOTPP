package com.zapcorp.transformator;

import com.zapcorp.transformator.util.RandUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.function.Function;

@Slf4j
public class ListTransformatorApp {

    public static final String TARGET_CLASSES_PATH = "./Lab4/target/classes";

    private static final int TWO = 2;

    private static final int PROCESSES_NUM_POWER = (int) Math.pow(TWO, 3);
    private static final int MEASUREMENT_COUNT = (int) Math.pow(TWO, 3);
    private static final String RESULT_FILE_PATH = "/lab2/res/measurements.json";

    private static final int LIST_SIZE = (int) Math.pow(TWO, 19);
    private static final double ELEMENTS_ORIGIN = Math.pow(TWO, 1);
    private static final double ELEMENTS_BOUND = Math.pow(TWO, 23);
    private static final Comparator<Double> COMPARING_CRITERIA = Comparator.naturalOrder();

    private static final Function<Double, Double> FUNCTION = (Function<Double, Double> & Serializable)
            (x) -> Math.sqrt(Math.pow(TWO, 7) * Math.pow(x, -Math.sqrt(x)) / Math.pow(TWO, 6)) + x / Math.pow(TWO, 2);


    public static void main(final String... args) throws IOException {
        clearFiles();

        log.info("Transformations measurement started..");
        final var start = System.currentTimeMillis();

        new ConcurrentListTransformer(
                PROCESSES_NUM_POWER,
                RandUtil.generateList(LIST_SIZE, ELEMENTS_ORIGIN, ELEMENTS_BOUND),
                COMPARING_CRITERIA,
                FUNCTION,
                MEASUREMENT_COUNT,
                RESULT_FILE_PATH
        ).runTransformations();

        log.info("Transformations measurement finished successfully. It took {} seconds.",
                (System.currentTimeMillis() - start) / 1000);
    }

    private static void clearFiles() throws IOException {
        Files.newBufferedWriter(Path.of(TARGET_CLASSES_PATH + RESULT_FILE_PATH),
                StandardOpenOption.TRUNCATE_EXISTING).close();
    }
}