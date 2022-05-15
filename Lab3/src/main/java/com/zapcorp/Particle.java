package com.zapcorp;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class Particle implements Runnable {

    private final float rightMoveProbability;
    private final Crystal crystal;
    private final ParticleStrategy particleStrategy;
    private final BrownianMotionEmulation.EmulationState emulationState;


    @Override
    public void run() {
        particleStrategy.emulateParticle(rightMoveProbability, crystal);
        emulationState.notifyFinished();
    }
}
