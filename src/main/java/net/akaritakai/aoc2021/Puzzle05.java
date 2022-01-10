package net.akaritakai.aoc2021;

import java.util.regex.Pattern;

/**
 * In Day 5, we are given a bunch of horizontal, vertical, and diagonal lines to draw and told to count all the discrete
 * points that are intersected by at least two lines.
 *
 * Our particular puzzle input is bound between (0,0) and (999,999) and so we can optimize to use only a 1000x1000
 * array to store our intersections and then query it at the end.
 */
public class Puzzle05 extends AbstractPuzzle {
    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    /*
     * All the points (x, y) are has 0 <= x <= 1000 and 0 <= y <= 1000.
     */
    private final int[][] diagonalLines = new int[1000][1000];
    private final int[][] nonDiagonalLines = new int[1000][1000];

    public Puzzle05(String puzzleInput) {
        super(puzzleInput);
        getPuzzleInput().lines().forEach(line -> {
            var matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                var x1 = Integer.parseInt(matcher.group(1));
                var y1 = Integer.parseInt(matcher.group(2));
                var x2 = Integer.parseInt(matcher.group(3));
                var y2 = Integer.parseInt(matcher.group(4));
                var dx = Integer.signum(x2 - x1);
                var dy = Integer.signum(y2 - y1);
                var isDiagonal = x1 != x2 && y1 != y2;
                var x = x1;
                var y = y1;
                while (x != x2 + dx || y != y2 + dy) {
                    if (isDiagonal) {
                        diagonalLines[x][y]++;
                    } else {
                        nonDiagonalLines[x][y]++;
                    }
                    x += dx;
                    y += dy;
                }
            }
        });
    }

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public String solvePart1() {
        var count = 0;
        for (var x = 0; x < 1000; x++) {
            for (var y = 0; y < 1000; y++) {
                if (nonDiagonalLines[x][y] > 1) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    @Override
    public String solvePart2() {
        var count = 0;
        for (var x = 0; x < 1000; x++) {
            for (var y = 0; y < 1000; y++) {
                if (diagonalLines[x][y] + nonDiagonalLines[x][y] > 1) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }
}