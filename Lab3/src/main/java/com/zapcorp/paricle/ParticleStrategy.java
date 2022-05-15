package com.zapcorp.paricle;

import com.zapcorp.Crystal;

public interface ParticleStrategy {

    void emulateParticle(float rightMoveProbability, Crystal crystal);
}
