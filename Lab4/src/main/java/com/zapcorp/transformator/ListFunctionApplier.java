package com.zapcorp.transformator;

import com.zapcorp.transformator.util.SerializationUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


@Slf4j
public class ListFunctionApplier {

    public static void main(String... args) throws IOException {
        final var inputFileLines = Files.readAllLines(Path.of(args[0]));
        final var processes = Integer.parseInt(args[1]);
        final var process = Integer.parseInt(args[2]);

        final var function = SerializationUtils.functionFromString(inputFileLines.get(0));
        final var list = SerializationUtils.listFromString(inputFileLines.get(1));
        final var comparator = SerializationUtils.comparatorFromString(inputFileLines.get(2));

        var min = Double.MAX_VALUE;
        for (int i = process; i < list.size(); i += processes) {
            final var calculatedValue = function.apply(list.get(i));
            min = comparator.compare(min, calculatedValue) > 0 ? calculatedValue : min;
        }

        Files.write(Path.of(args[3]), List.of(String.valueOf(min)), StandardOpenOption.APPEND);
        log.info("Process {}, Min {}", process, min);


        System.exit(0);
    }
}
