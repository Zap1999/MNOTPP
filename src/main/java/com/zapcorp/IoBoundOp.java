package com.zapcorp;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;


@RequiredArgsConstructor
public class IoBoundOp implements Runnable {

    private final Random random = new Random(1000L);
    private final String inputFilePath;
    private final String outputFilePath;


    @Override
    @SneakyThrows
    public void run() {
        final var loader = Thread.currentThread().getContextClassLoader();
        final var outputFilePathRand = outputFilePath + random.nextInt();

        Files.copy(
                Path.of(Objects.requireNonNull(loader.getResource(inputFilePath)).getPath()),
                Path.of(Objects.requireNonNull(loader.getResource(".")).getPath() + outputFilePathRand),
                StandardCopyOption.REPLACE_EXISTING);
        Files.deleteIfExists(Path.of(Objects.requireNonNull(loader.getResource(outputFilePathRand)).getPath()));
    }
}
