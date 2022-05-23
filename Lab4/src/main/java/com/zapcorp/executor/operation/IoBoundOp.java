package com.zapcorp.executor.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Random;


public class IoBoundOp {

    private static final Random RANDOM = new Random(1000L);


    public static void main(String... args) throws IOException {
        final var inputFilePath = args[0];
        final var outputFilePath = args[1];

        final var loader = Thread.currentThread().getContextClassLoader();
        final var outputFilePathRand = outputFilePath + RANDOM.nextInt();

        Files.copy(
                Path.of(Objects.requireNonNull(loader.getResource(inputFilePath)).getPath()),
                Path.of(Objects.requireNonNull(loader.getResource(".")).getPath() + outputFilePathRand),
                StandardCopyOption.REPLACE_EXISTING);
        Files.deleteIfExists(Path.of(Objects.requireNonNull(loader.getResource(outputFilePathRand)).getPath()));
    }
}
