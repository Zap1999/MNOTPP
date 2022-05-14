package com.zapcorp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;


@Slf4j
public class Main {

    private static final int THREADS_NUM_POWER = 8;
    private static final int MEASUREMENT_COUNT = 8;

    private static final int CPU_BOUND_OP_PI_DIGITS = 4096;
    private static final String CPU_BOUND_OP_RESULT_FILE_PATH = "./res/cpu-bound.json";

    private static final String IO_BOUND_OP_INPUT_FILE_PATH = "./io-bound/enwik9";
    private static final String IO_BOUND_OP_OUTPUT_FILE_PATH = "./io-bound/enwik9-out";
    private static final String IO_BOUND_OP_RESULT_FILE_PATH = "./res/io-bound.json";

    private static final int MEMORY_BOUND_OP_ALLOC_BYTES = 32_000_000; // 32 Mb
    private static final String MEMORY_BOUND_OP_RESULT_FILE_PATH = "./res/memory-bound.json";


    public static void main(String... args) throws IOException {
        clearFiles();

        try {
            log.info("CPU-bound operations measurement started..");
            final var start = System.currentTimeMillis();
            new OpExecutor(
                    THREADS_NUM_POWER,
                    new CpuBoundOp(CPU_BOUND_OP_PI_DIGITS),
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
                    THREADS_NUM_POWER,
                    new MemoryBoundOp(MEMORY_BOUND_OP_ALLOC_BYTES),
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
                    THREADS_NUM_POWER,
                    new IoBoundOp(IO_BOUND_OP_INPUT_FILE_PATH, IO_BOUND_OP_OUTPUT_FILE_PATH),
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
