package net.akaritakai.aoc2021;

/**
 * In Day 1, we're tasked with finding the number of sliding measurements windows of a given size whose sum increases
 * between samples. This is done in O(n).
 */
public class Puzzle01 extends AbstractPuzzle {
    public Puzzle01(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 1;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(countIncreases(1));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(countIncreases(3));
    }

    private int countIncreases(int windowSize) {
        var depths = getPuzzleInput().lines().mapToLong(Long::parseLong).toArray();
        var count = 0;
        var prevSum = 0L;
        for (var i = 0; i < windowSize; i++) {
            prevSum += depths[i];
        }
        for (var i = windowSize; i < depths.length; i++) {
            var sum = prevSum + depths[i] - depths[i - windowSize];
            if (sum > prevSum) {
                count++;
            }
            prevSum = sum;
        }
        return count;
    }
}