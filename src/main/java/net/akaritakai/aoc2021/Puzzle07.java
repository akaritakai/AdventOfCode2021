package net.akaritakai.aoc2021;

import java.util.Arrays;

/**
 * In Day 7, we are given a number of positions of crabs and asked to find the minimum amount of fuel it would take to
 * line them up.
 *
 * In part 1, the cost of fuel is a linear function of distance, so, we are looking for the crabs to line up to a
 * position that minimizes the sum of the distances they would need to travel. This simplifies to asking what the fuel
 * cost is to travel to the median of positions.
 *
 * In part 2, the cost of fuel is a quadratic function of distance (specifically the triangular number). After some
 * careful math, we can find that the minimum possible fuel use will occur for some position that is within 0.5 of the
 * mean of the crab's positions.
 */
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