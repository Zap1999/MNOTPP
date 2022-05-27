package com.zapcorp.transformator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zapcorp.transformator.util.SerializationUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.zapcorp.transformator.ListTransformatorApp.TARGET_CLASSES_PATH;

@Slf4j
@RequiredArgsConstructor
public class ConcurrentListTransformer {

    private static final int TWO = 2;
    private static final String INPUT_FILE_PATH = TARGET_CLASSES_PATH + "/lab2/input.txt";
    private static final String RESULT_FILE_PATH = TARGET_CLASSES_PATH + "/lab2/res/calculation.txt";


    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private final int powerOfTwoProcesses;
    private final List<Double> list;
    private final Comparator<Double> comparator;
    private final Function<Double, Double> transformationFunction;
    private final int measurementIterations;
    private final String resultFilePath;


    @SneakyThrows
    public void runTransformations() {
        final var result = IntStream.range(0, powerOfTwoProcesses)
                .map(power -> (int) Math.pow(TWO, power))
                .mapToObj(this::executeOp)
                .collect(Collectors.toList());

        objectMapper.writeValue(new File(Path.of(TARGET_CLASSES_PATH + resultFilePath).toUri()), result);
    }

    @SneakyThrows
    private ExecutionResult executeOp(int threads) {
        final var avgExecTimeNanos = LongStream.range(0L, measurementIterations)
                .mapToDouble(i -> measureExecTime(threads))
                .average();

        return new ExecutionResult(threads, avgExecTimeNanos.orElse(0.0) / 1E6);
    }

    @SneakyThrows
    private double measureExecTime(int processes) {
        final var processList = new ArrayList<ProcessBuilder>();
        for (int i = 0; i < processes; i++) {
            processList.add(createProcessBuilder(processes, i));
        }

        final var start = System.nanoTime();
        final var zeroStatuses = processList.stream()
                .map(processBuilder -> {
                    try {
                        return processBuilder.start();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(process -> {
                    try {
                        return process.waitFor();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(status -> status.equals(0))
                .mapToInt(i -> i)
                .count();

        final var min = Files.readAllLines(Path.of(RESULT_FILE_PATH)).stream()
                .mapToDouble(Double::parseDouble)
                .min();

        final var finish = System.nanoTime();
        if (zeroStatuses != processes) {
            throw new RuntimeException(
                    "Not all processes finished with 0 status. Expected: " + processes + "; Got: " + zeroStatuses);
        }
        log.info("Minimum val is: " + min);
        Files.newBufferedWriter(Path.of(RESULT_FILE_PATH), StandardOpenOption.TRUNCATE_EXISTING).close();

        return (finish - start) / (float) processes;
    }

    @SneakyThrows
    private ProcessBuilder createProcessBuilder(int processes, int process) {
        final var pb = new ProcessBuilder(generateCommand(processes, process));
        final var log = new File(Path.of(TARGET_CLASSES_PATH + "/lab2/res/log-" + process).toUri());
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));

        return pb;
    }

    @SneakyThrows
    private List<String> generateCommand(int processes, int process) {
        Files.writeString(Path.of(INPUT_FILE_PATH),
                SerializationUtils.toString(transformationFunction) + "\n" +
                        SerializationUtils.toString(new ArrayList<>(list)) + "\n" +
                        SerializationUtils.toString(comparator)
        );
        return List.of(
                "java",
                "-cp",
                System.getProperty("java.class.path"),
                ListFunctionApplier.class.getCanonicalName(),
                INPUT_FILE_PATH,
                Integer.toString(processes),
                Integer.toString(process),
                RESULT_FILE_PATH
        );
    }


    @Value
    private static class ExecutionResult {

        int processes;
        double execTimeMillis;
    }
}
