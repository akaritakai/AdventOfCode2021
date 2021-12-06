package net.akaritakai.aoc2021;

import java.util.Arrays;

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
        Arrays.stream(getPuzzleInput().trim().split(",")).map(Integer::parseInt).forEach(i -> fish[i]++);
        var base = 0;
        for (var day = 0; day < days; day++) {
            fish[(base + 7) % 9] += fish[base];
            base = (base + 1) % 9;
        }
        return Arrays.stream(fish).sum();
    }
}