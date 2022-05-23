package com.zapcorp.executor;

import com.zapcorp.executor.operation.Operation;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
public class OpExecutorApp {

    private static final int PROCESSES_NUM_POWER = 8;
    private static final int MEASUREMENT_COUNT = 8;

    private static final int CPU_BOUND_OP_PI_DIGITS = 4096;
    private static final String CPU_BOUND_OP_RESULT_FILE_PATH = "lab1/res/cpu-bound.json";

    private static final String IO_BOUND_OP_INPUT_FILE_PATH = "lab1/io-bound/enwik9";
    private static final String IO_BOUND_OP_OUTPUT_FILE_PATH = "lab1/io-bound/enwik9-out";
    private static final String IO_BOUND_OP_RESULT_FILE_PATH = "lab1/res/io-bound.json";

    private static final int MEMORY_BOUND_OP_ALLOC_BYTES = 32_000_000; // 32 Mb
    private static final String MEMORY_BOUND_OP_RESULT_FILE_PATH = "lab1/res/memory-bound.json";


    public static void main(String... args) throws IOException {
        clearFiles();

        try {
            log.info("CPU-bound operations measurement started..");
            final var start = System.currentTimeMillis();
            new OpExecutor(
                    PROCESSES_NUM_POWER,
                    Operation.CPU,
                    new ArrayList<>(List.of(String.valueOf(CPU_BOUND_OP_PI_DIGITS))),
                    MEASUREMENT_COUNT,
                    CPU_BOUND_OP_RESULT_FILE_PATH
            ).runOp();
            log.info("CPU-bound operations measurement finished. It took {} seconds.",
                    (System.currentTimeMillis() - start) / 1000);
        } catch (Throwable e) {
            log.error("Error occurred during CPU-bound ops measurement", e);
        }

        try {
            log.info("Memory-bound operations measurement started..");
            final var start = System.currentTimeMillis();
            new OpExecutor(
                    PROCESSES_NUM_POWER,
                    Operation.MEMORY,
                    new ArrayList<>(List.of(String.valueOf(MEMORY_BOUND_OP_ALLOC_BYTES))),
                    MEASUREMENT_COUNT,
                    MEMORY_BOUND_OP_RESULT_FILE_PATH
            ).runOp();
            log.info("Memory-bound operations measurement finished. It took {} seconds.",
                    (System.currentTimeMillis() - start) / 1000);
        } catch (Throwable e) {
            log.error("Error occurred during CPU-bound ops measurement", e);
        }

        try {
            log.info("IO-bound operations measurement started..");
            final var start = System.currentTimeMillis();
            new OpExecutor(
                    PROCESSES_NUM_POWER,
                    Operation.IO,
                    new ArrayList<>(List.of(IO_BOUND_OP_INPUT_FILE_PATH, IO_BOUND_OP_OUTPUT_FILE_PATH)),
                    MEASUREMENT_COUNT,
                    IO_BOUND_OP_RESULT_FILE_PATH
            ).runOp();
            log.info("IO-bound operations measurement finished. It took {} seconds.",
                    (System.currentTimeMillis() - start) / 1000);
        } catch (Throwable e) {
            log.error("Error occurred during CPU-bound ops measurement", e);
        }
    }

    private static void clearFiles() throws IOException {
        final var loader = Thread.currentThread().getContextClassLoader();

        Files.newBufferedWriter(
                Path.of(Objects.requireNonNull(loader.getResource(CPU_BOUND_OP_RESULT_FILE_PATH)).getPath()),
                StandardOpenOption.TRUNCATE_EXISTING).close();
        Files.newBufferedWriter(
                Path.of(Objects.requireNonNull(loader.getResource(IO_BOUND_OP_RESULT_FILE_PATH)).getPath()),
                StandardOpenOption.TRUNCATE_EXISTING).close();
        Files.newBufferedWriter(
                Path.of(Objects.requireNonNull(loader.getResource(MEMORY_BOUND_OP_RESULT_FILE_PATH)).getPath()),
                StandardOpenOption.TRUNCATE_EXISTING).close();
    }
}
