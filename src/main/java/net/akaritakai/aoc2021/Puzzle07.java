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
        var cost = 0;
        for (var position : crabPositions) {
            cost += Math.abs(position - median);
        }
        return String.valueOf(cost);
    }

    @Override
    public String solvePart2() {
        double mean = 0;
        for (var position : crabPositions) {
            mean += position;
        }
        mean /= crabPositions.length;
        var floorCost = 0;
        var ceilCost = 0;
        for (var position : crabPositions) {
            var floorDistance = Math.abs(position - Math.floor(mean));
            floorCost += floorDistance * (floorDistance + 1) / 2;
            var ceilDistance = Math.abs(position - Math.ceil(mean));
            ceilCost += ceilDistance * (ceilDistance + 1) / 2;
        }
        return String.valueOf(Math.min(floorCost, ceilCost));
    }
}