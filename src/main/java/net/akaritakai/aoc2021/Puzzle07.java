package net.akaritakai.aoc2021;

import java.util.Arrays;

public class Puzzle07 extends AbstractPuzzle {
    private final int[] crabPositions;

    public Puzzle07(String puzzleInput) {
        super(puzzleInput);
        crabPositions = Arrays.stream(getPuzzleInput().trim().split(","))
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();
    }

    @Override
    public int getDay() {
        return 7;
    }

    @Override
    public String solvePart1() {
        var median = crabPositions[crabPositions.length / 2];
        var cost = Arrays.stream(crabPositions).map(x -> Math.abs(x - median)).sum();
        return String.valueOf(cost);
    }

    @Override
    public String solvePart2() {
        var mean = Arrays.stream(crabPositions).sum() / (double) crabPositions.length;
        var floorCost = Arrays.stream(crabPositions)
                .map(x -> (int) Math.abs(x - Math.floor(mean)))
                .map(x -> x * (x + 1) / 2)
                .sum();
        var ceilCost = Arrays.stream(crabPositions)
                .map(x -> (int) Math.abs(x - Math.ceil(mean)))
                .map(x -> x * (x + 1) / 2)
                .sum();
        return String.valueOf(Math.min(floorCost, ceilCost));
    }
}