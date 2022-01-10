package net.akaritakai.aoc2021;

import java.util.Arrays;

/**
 * In Day 6, we are given a counting problem. We have a number of fish which reproduce every 7 days. We are given an
 * instance of fish and told their current timers for reproducing. We are asked to find how many fish there are after n
 * days.
 *
 * Keeping each fish in memory is folly as the number of fish grow exponentially. Instead, we can keep count of how many
 * fish are at a given timer each day and then figure out what the next day's count is.
 *
 * This gives us a complexity of O(n).
 */
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