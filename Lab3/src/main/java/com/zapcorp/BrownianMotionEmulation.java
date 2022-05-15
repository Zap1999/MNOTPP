package com.zapcorp;

import com.zapcorp.paricle.IterativeParticleStrategy;
import com.zapcorp.paricle.Particle;
import com.zapcorp.paricle.TimedParticleStrategy;
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


    public BrownianMotionEmulation(int crystalSize, int numParticles, float rightMoveProbability, int numIterations) {
        validate(crystalSize, numParticles);

        this.numParticles = numParticles;
        this.rightMoveProbability = rightMoveProbability;
        this.crystal = new Crystal(crystalSize, numParticles);
        this.emulationState = new EmulationState(numParticles);
        this.particles = generateIterativeParticles(numIterations);
    }

    public BrownianMotionEmulation(int crystalSize, int numParticles, float rightMoveProbability,
                                   int executionTimeMillis, int iterationDelayMillis) {
        validate(crystalSize, numParticles);

        this.numParticles = numParticles;
        this.rightMoveProbability = rightMoveProbability;
        this.crystal = new Crystal(crystalSize, numParticles);
        this.emulationState = new EmulationState(numParticles);
        this.particles = generateTimedParticles(executionTimeMillis, iterationDelayMillis);
    }

    private void validate(int crystalSize, int particles) {
        if (crystalSize <= 0) {
            throw new RuntimeException("Crystal size must be more than 0.");
        }
        if (particles <= 0) {
            throw new RuntimeException("Particles must be more than 0.");
        }
    }

    private Collection<Thread> generateIterativeParticles(int numIterations) {
        final var arr = new Thread[numParticles];
        for (int i = 0; i < numParticles; i++) {
            arr[i] = new Thread(new Particle(rightMoveProbability, crystal,
                    new IterativeParticleStrategy(numIterations), emulationState));
        }
        return Arrays.asList(arr);
    }

    private Collection<Thread> generateTimedParticles(int executionTimeMillis, int iterationDelayMiliis) {
        final var arr = new Thread[numParticles];
        for (int i = 0; i < numParticles; i++) {
            arr[i] = new Thread(new Particle(rightMoveProbability, crystal,
                    new TimedParticleStrategy(executionTimeMillis, iterationDelayMiliis), emulationState));
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
    public final static class EmulationState {

        private final Object finishedLock = new Object();

        private final int numParticles;

        private int finished = 0;


        public void notifyFinished() {
            synchronized (finishedLock) {
                finished++;
            }
        }

        public boolean isFinished() {
            synchronized (finishedLock) {
                return finished >= numParticles;
            }
        }
    }
}
