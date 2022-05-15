package com.zapcorp;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Collection;

public class BrownianMotionEmulation {

    private static final int UPDATE_FREQUENCY_HZ = 10;

    private final int numParticles;
    private final float rightMoveProbability;
    private final Crystal crystal;
    private final EmulationState emulationState;
    private final Collection<Thread> particles;


    public BrownianMotionEmulation(int crystalSize, int numParticles, float rightMoveProbability,
                                   ParticleStrategy particleStrategy) {
        validate(crystalSize, numParticles);

        this.numParticles = numParticles;
        this.rightMoveProbability = rightMoveProbability;
        this.crystal = new Crystal(crystalSize, numParticles);
        this.emulationState = new EmulationState(numParticles);
        this.particles = generateParticles(particleStrategy);
    }

    private void validate(int crystalSize, int particles) {
        if (crystalSize <= 0) {
            throw new RuntimeException("Crystal size must be more than 0.");
        }
        if (particles <= 0) {
            throw new RuntimeException("Particles must be more than 0.");
        }
    }

    private Collection<Thread> generateParticles(ParticleStrategy particleStrategy) {
        final var arr = new Thread[numParticles];
        for (int i = 0; i < numParticles; i++) {
            arr[i] = new Thread(new Particle(rightMoveProbability, crystal, particleStrategy, emulationState));
        }
        return Arrays.asList(arr);
    }

    @SneakyThrows
    @SuppressWarnings("BusyWait")
    public void run() {

        particles.forEach(Thread::start);

        while (!emulationState.isFinished()) {
            System.out.println(Arrays.toString(crystal.getCrystalState()));
            Thread.sleep(1000 / UPDATE_FREQUENCY_HZ);
        }
    }


    @ThreadSafe
    @RequiredArgsConstructor
    public static class EmulationState {

        private final int numParticles;

        private int finished = 0;


        public synchronized void notifyFinished() {
            finished++;
        }

        public synchronized boolean isFinished() {
            return finished >= numParticles;
        }
    }
}
