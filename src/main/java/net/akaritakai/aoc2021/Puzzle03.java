package net.akaritakai.aoc2021;

import java.util.Arrays;

/**
 * In Day 3, we are working with a list of binary strings.
 *
 * In part 1, we have to iterate over all the strings and determine the most common bit in each position. We do this in
 * O(n*k) time, where n is the number of strings and k is the length of the strings.
 *
 * In part 2, we have a similar problem but based on our finding of the most common bit in each position, we narrow down
 * our search to only those strings in our list that have the same bit in the same position. We can do this naively in
 * O(n*k) time with O(n) removal operations. However, with some cleverness, we can do this in O(log n) removal
 * operations:
 * - First, we sort the list in O(n*k) time via radix sort or O(n*log n) time (whichever is faster).
 * - Secondly, we use binary search to find the first string in the list that has a different bit in the position we're
 *   searching through and remove either all the strings before it or all the strings after it.
 */
public class Puzzle03 extends AbstractPuzzle {
    private final String[] report;
    private final int length;
    private boolean sorted;

    public Puzzle03(String puzzleInput) {
        super(puzzleInput);
        report = getPuzzleInput().lines().toArray(String[]::new);
        length = report[0].length();
    }

    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public String solvePart1() {
        var gamma = 0L;
        var epsilon = 0L;
        for (var i = 0; i < length; i++) {
            var zeros = 0L;
            var ones = 0L;
            for (var s : report) {
                if (s.charAt(i) == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            if (zeros > ones) {
                gamma <<= 1;
                epsilon = (epsilon << 1) | 1;
            } else {
                gamma = (gamma << 1) | 1;
                epsilon <<= 1;
            }
        }
        return String.valueOf(gamma * epsilon);
    }

    @Override
    public String solvePart2() {
        if (!sorted) {
            Arrays.sort(report); // Can be replaced with a radix sort if k < log n
            sorted = true;
        }
        var low = 0;
        var high = report.length;
        for (var i = 0; i < length && high - low > 1; i++) {
            var mid = findMid(report, i, low, high);
            if (high - mid >= (high - low + 1) / 2) {
                low = mid; // 1s are as or more common than 0s in this position
            } else {
                high = mid; // 0s are more common in this position
            }
        }
        var oxygenRating = Long.parseLong(report[low], 2);
        low = 0;
        high = report.length;
        for (var i = 0; i < length && high - low > 1; i++) {
            var mid = findMid(report, i, low, high);
            if (high - mid >= (high - low + 1) / 2) {
                high = mid; // 1s are as or more common than 0s in this position
            } else {
                low = mid; // 0s are more common in this position
            }
        }
        var co2Rating = Long.parseLong(report[low], 2);
        return String.valueOf(oxygenRating * co2Rating);
    }

    /**
     * Perform a binary search on the sorted report between low inclusive and high exclusive to find the first binary
     * string that has 1s bit in the given position we're searching through. If no such string exists, return high.
     *
     * @param report the sorted report of binary strings
     * @param position the position of the bit to examine in each string
     * @param low the inclusive lower bound of the search
     * @param high the exclusive upper bound of the search
     * @return the index of the first string that has a 1 in the given position, or high if no such string exists
     */
    private static int findMid(String[] report, int position, int low, int high) {
        while (low < high) {
            var mid = low + (high - low) / 2;
            if (report[mid].charAt(position) == '1') {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}