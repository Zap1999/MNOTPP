package com.zapcorp.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zapcorp.executor.operation.Operation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


@RequiredArgsConstructor
public class OpExecutor {

    private static final int TWO = 2;
    private static final String TARGET_CLASSES_PATH = "./Lab4/target/classes";

    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private final int powerOfTwoProcesses;
    private final Operation operation;
    private final List<String> args;
    private final int measurementIterations;
    private final String resultFilePath;


    @SneakyThrows
    public void runOp() {
        final var result = IntStream.range(0, powerOfTwoProcesses)
                .map(power -> (int) Math.pow(TWO, power))
                .mapToObj(this::executeOp)
                .collect(Collectors.toList());

        objectMapper.writeValue(new File(Objects.requireNonNull(
                Thread.currentThread().getContextClassLoader().getResource(resultFilePath)).getPath()), result);
    }

    @SneakyThrows
    private ExecutionResult executeOp(int processes) {
        final var avgExecTimeNanos = LongStream.range(0L, measurementIterations)
                .mapToDouble(i -> measureExecTime(processes))
                .average();

        return new ExecutionResult(processes, avgExecTimeNanos.orElse(0.0) / 1E6);
    }

    @SneakyThrows
    private double measureExecTime(int processes) {
        final var processList = new ArrayList<ProcessBuilder>();
        for (int i = 0; i < processes; i++) {
            processList.add(createProcessBuilder());
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
        final var finish = System.nanoTime();
        if (zeroStatuses != processes) {
            throw new RuntimeException(
                    "Not all processes finished with 0 status. Expected: " + processes + "; Got: " + zeroStatuses);
        }

        return (finish - start) / (float) processes;
    }

    private ProcessBuilder createProcessBuilder() {
        final var pb = new ProcessBuilder(generateCommand());
        final var log = new File(TARGET_CLASSES_PATH + "/lab1/res/log-" + operation.getCls());
        pb.redirectErrorStream(true);
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));

        return pb;
    }

    private List<String> generateCommand() {
        final var command = new ArrayList<>(args);
        command.add(0, operation.getClass().getPackageName() + "." + operation.getCls());
        command.add(0, TARGET_CLASSES_PATH);
        command.add(0, "-cp");
        command.add(0, "java");

        return command;
    }


    @Value
    private static class ExecutionResult {

        int processes;
        double execTimeMillis;
    }
}
