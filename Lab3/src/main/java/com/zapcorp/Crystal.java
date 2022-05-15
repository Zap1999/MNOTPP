package com.zapcorp;

import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.List;


@ThreadSafe
public final class Crystal {

    private final Object movementLock = new Object();

    private final List<Integer> crystal;


    public Crystal(int crystalSize, int numParticles) {
        final var arr = new Integer[crystalSize];
        Arrays.fill(arr, 0);
        arr[0] = numParticles;
        this.crystal = Arrays.asList(arr);
    }

    public int moveRight(int position) {
        synchronized (movementLock) {
            final var newPosition = position + 1;
            if (newPosition < crystal.size()) {
                crystal.set(position, crystal.get(position) - 1);
                crystal.set(newPosition, crystal.get(newPosition) + 1);
                return newPosition;
            } else {
                return position;
            }
        }
    }

    public int moveLeft(int position) {
        synchronized (movementLock) {
            final var newPosition = position - 1;
            if (newPosition >= 0) {
                crystal.set(position, crystal.get(position) - 1);
                crystal.set(newPosition, crystal.get(newPosition) + 1);
                return newPosition;
            } else {
                return position;
            }
        }
    }

    public int[] getCrystalState() {
        synchronized (movementLock) {
            return crystal.stream()
                    .mapToInt(i -> i)
                    .toArray();
        }
    }
}
