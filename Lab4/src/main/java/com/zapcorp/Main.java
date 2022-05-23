package com.zapcorp;

import com.zapcorp.executor.OpExecutorApp;
import com.zapcorp.transformator.ListTransformatorApp;

import java.io.IOException;

public class Main {

    private static final int APP = 1;


    public static void main(String[] args) throws IOException {
        if (APP == 1) {
            OpExecutorApp.main();
        } else {
            ListTransformatorApp.main();
        }
    }
}
