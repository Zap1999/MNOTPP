package com.zapcorp;

import java.util.function.Function;

public class Main {

    private static final Function<Integer, Double> FUNCTION =
            (x) -> MathUtil.sinh(MathUtil.factorial(x)) / MathUtil.cosh(MathUtil.factorial(-(2 * x)));

    public static void main(final String... args) {

    }
}