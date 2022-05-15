package com.zapcorp.paricle;

import com.zapcorp.Crystal;
import lombok.RequiredArgsConstructor;

import java.util.Random;


@RequiredArgsConstructor
public class TimedParticleStrategy implements ParticleStrategy {

    private final Random random = new Random();

    private final int execTime;
    private final int iterDelay;

    private int position = 0;


    @Override
    @SuppressWarnings("BusyWait")
    public void emulateParticle(float rightMoveProbability, Crystal crystal) {
        final var start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < execTime) {
            if (random.nextFloat() < rightMoveProbability) {
                position = crystal.moveRight(position);
            } else {
                position = crystal.moveLeft(position);
            }

            try {
                Thread.sleep(iterDelay);
            } catch (InterruptedException e) {
                System.err.println("Exception occurred during particle emulation.\n" + e.getMessage());
            }
        }
    }
}
