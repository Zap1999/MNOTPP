package com.zapcorp;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class Particle implements Runnable {

    private final float rightMoveProbability;
    private final Crystal crystal;
    private final ParticleStrategy particleStrategy;


    @Override
    public void run() {
        particleStrategy.startParticleEmulation(rightMoveProbability, crystal);
    }
}
