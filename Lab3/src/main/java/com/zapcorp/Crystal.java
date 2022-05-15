package com.zapcorp;

import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.List;


@ThreadSafe
public final class Crystal {

    private final List<Integer> crystal;


    public Crystal(int crystalSize, int numParticles) {
        final var arr = new Integer[crystalSize];
        Arrays.fill(arr, 0);
        arr[0] = numParticles;
        this.crystal = Arrays.asList(arr);
    }

    public synchronized int moveRight(int position) {
        final var newPosition = position + 1 < crystal.size() ? position + 1 : position;
        crystal.set(position, crystal.get(position) - 1);
        crystal.set(newPosition, crystal.get(newPosition) + 1);
        return newPosition;
    }

    public synchronized int moveLeft(int position) {
        final var newPosition = position - 1 >= 0 ? position - 1 : position;
        crystal.set(position, crystal.get(position) - 1);
        crystal.set(newPosition, crystal.get(newPosition) + 1);
        return newPosition;
    }

    public synchronized int[] getCrystalState() {
        return crystal.stream()
                .mapToInt(i -> i)
                .toArray();
    }
}
