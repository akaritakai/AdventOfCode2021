package net.akaritakai.aoc2021;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Puzzle06 extends AbstractPuzzle {
    public Puzzle06(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 6;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(simulate(80));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(simulate(256));
    }

    private long simulate(int days) {
        var fish = new long[9];
        for (var token : getPuzzleInput().trim().split(",")) {
            var value = Integer.parseInt(token);
            fish[value]++;
        }
        var newFish = 0L;
        for (var day = 0; day < days; day++) {
            newFish = fish[0];
            //noinspection SuspiciousSystemArraycopy
            System.arraycopy(fish, 1, fish, 0, 8);
            fish[6] += newFish;
            fish[8] = newFish;
        }
        return Arrays.stream(fish).sum();
    }

}