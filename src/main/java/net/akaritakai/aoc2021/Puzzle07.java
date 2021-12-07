package net.akaritakai.aoc2021;

import java.util.Arrays;

public class Puzzle07 extends AbstractPuzzle {
    private final int[] crabPositions;
    private final int minPosition;
    private final int maxPosition;

    public Puzzle07(String puzzleInput) {
        super(puzzleInput);
        crabPositions = Arrays.stream(getPuzzleInput().trim().split(",")).mapToInt(Integer::parseInt).toArray();
        minPosition = Arrays.stream(crabPositions).min().orElseThrow();
        maxPosition = Arrays.stream(crabPositions).max().orElseThrow();
    }

    @Override
    public int getDay() {
        return 7;
    }

    @Override
    public String solvePart1() {
        var minCost = Integer.MAX_VALUE;
        for (var i = minPosition; i <= maxPosition; i++) {
            var cost = 0;
            for (var position : crabPositions) {
                cost += Math.abs(position - i);
            }
            minCost = Math.min(cost, minCost);
        }
        return String.valueOf(minCost);
    }

    @Override
    public String solvePart2() {
        var minCost = Integer.MAX_VALUE;
        for (var i = minPosition; i <= maxPosition; i++) {
            var cost = 0;
            for (var position : crabPositions) {
                var distance = Math.abs(position - i);
                cost += distance * (distance + 1) / 2;
            }
            minCost = Math.min(cost, minCost);
        }
        return String.valueOf(minCost);
    }
}