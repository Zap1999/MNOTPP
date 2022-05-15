package com.zapcorp;

import java.util.Arrays;
import java.util.Collection;

public class BrownianMotionEmulation {

    private final int crystalSize;
    private final int numParticles;
    private final float rightMoveProbability;
    private final Crystal crystal;
    private final Collection<Particle> particles;


    public BrownianMotionEmulation(int crystalSize, int numParticles, float rightMoveProbability,
                                   ParticleStrategy particleStrategy) {
        validate(crystalSize, numParticles);

        this.crystalSize = crystalSize;
        this.numParticles = numParticles;
        this.rightMoveProbability = rightMoveProbability;
        this.crystal = new Crystal(crystalSize, numParticles);
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

    private Collection<Particle> generateParticles(ParticleStrategy particleStrategy) {
        final var arr = new Particle[numParticles];
        for (int i = 0; i < numParticles; i++) {
            arr[i] = new Particle(rightMoveProbability, crystal, particleStrategy);
        }
        return Arrays.asList(arr);
    }

    public void run() {

    }
}
