package com.zapcorp;

import com.zapcorp.util.RandUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class Main {

    private static final int LIST_SIZE = 500_000;
    private static final int ELEMENTS_ORIGIN = 2;
    private static final int ELEMENTS_BOUND = 10_000_000;

    private static final Function<Integer, Double> FUNCTION = (x) -> Math.sqrt(123 * Math.pow(x, -Math.sqrt(x)) / 68) + x / 3;


    public static void main(final String... args) {
        final var list = RandUtil.generateList(LIST_SIZE, ELEMENTS_ORIGIN, ELEMENTS_BOUND);


        log.info("");
    }
}