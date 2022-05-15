package com.zapcorp;

import lombok.RequiredArgsConstructor;

import java.util.Random;


@RequiredArgsConstructor
public class IterativeParticleStrategy implements ParticleStrategy {

    private final Random random = new Random();

    private final int numIterations;

    private int position = 0;


    @Override
    public void emulateParticle(float rightMoveProbability, Crystal crystal) {
        for (int i = 0; i < numIterations; i++) {
            if (random.nextFloat() < rightMoveProbability) {
                position = crystal.moveRight(position);
            } else {
                position = crystal.moveLeft(position);
            }
        }
    }
}
