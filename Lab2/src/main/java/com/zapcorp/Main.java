package com.zapcorp;

import com.zapcorp.util.MathUtil;
import com.zapcorp.util.RandUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class Main {

    private static final int LIST_SIZE = 500_000;
    private static final int ELEMENTS_ORIGIN = 2;
    private static final int ELEMENTS_BOUND = 10_000;

    private static final Function<Integer, Double> FUNCTION =
            (x) -> MathUtil.sinh(MathUtil.factorial(x)) / MathUtil.cosh(MathUtil.factorial(-(2 * x)));


    public static void main(final String... args) {
        final var list = RandUtil.generateList(LIST_SIZE, ELEMENTS_ORIGIN, ELEMENTS_BOUND);


    }
}