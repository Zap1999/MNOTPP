package com.zapcorp.executor.operation;

import java.util.Arrays;


public class MemoryBoundOp {

    public static void main(String... args) {
        final var bytes = Integer.parseInt(args[0]);

        final var array = new int[bytes / Integer.BYTES];
        Arrays.fill(array, 666);
        final var el = array[20];
    }
}
