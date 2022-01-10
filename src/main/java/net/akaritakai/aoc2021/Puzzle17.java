package net.akaritakai.aoc2021;

import java.util.regex.Pattern;

/**
 * In Day 17, we are given a trajectory problem. We are given an initial position and a target range to hit and told to
 * find the number of discrete velocities that will hit the range on a discrete time step.
 *
 * This problem involves identifying all relevant initial velocities and checking if they are satisfiable or not. Given
 * the tight bounds of the problem, this was solved by brute force (on my machine in ~50 ms).
 *
 * There are some easy closed-form shortcuts for this, but some of them make assumptions about the given target range.
 * For example, if the x-axis's target range contains a triangle number, then there is a closed form solution of
 * y1 * (y1 + 1) / 2 for part 1.
 */
public class Puzzle17 extends AbstractPuzzle {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;
    private final int minDx;
    private final int maxDx;
    private final int minDy;
    private final int maxDy;
    private final int maxSteps;

    public Puzzle17(String puzzleInput) {
        super(puzzleInput);
        var pattern = Pattern.compile("target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)");
        var matcher = pattern.matcher(puzzleInput);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid puzzle input");
        }
        x1 = Integer.parseInt(matcher.group(1));
        x2 = Integer.parseInt(matcher.group(2));
        y1 = Integer.parseInt(matcher.group(3));
        y2 = Integer.parseInt(matcher.group(4));
        minDx = x1 < 0 ? x1 - 1 : (int) Math.ceil(0.5 * (Math.sqrt(8 * Math.abs(x1) + 1) - 1));
        maxDx = x1 < 0 ? (int) -Math.ceil(0.5 * (Math.sqrt(8 * Math.abs(x2) + 1) - 1)) : x2 + 1;
        minDy = y1 < 0 ? y1 - 1 : (int) Math.ceil(0.5 * (Math.sqrt(8 * Math.abs(y1) + 1) - 1));
        maxDy = y1 < 0 ? Math.abs(y1 - 1) : y2 + 1;
        var maxXSteps = Math.abs(minDx);
        var maxYSteps = y1 > 0 ? Math.abs(maxDy) + 1 : 2 * (Math.abs(maxDy) + 1);
        maxSteps = Math.max(maxXSteps, maxYSteps);
    }

    @Override
    public int getDay() {
        return 17;
    }

    @Override
    public String solvePart1() {
        var maxHeight = Integer.MIN_VALUE;
        for (var dx = minDx; dx <= maxDx; dx++) {
            for (var dy = minDy; dy <= maxDy; dy++) {
                maxHeight = Math.max(maxHeight, findMaxHeight(dx, dy));
            }
        }
        return String.valueOf(maxHeight);
    }

    @Override
    public String solvePart2() {
        var count = 0;
        for (var dx = minDx; dx <= maxDx; dx++) {
            for (var dy = minDy; dy <= maxDy; dy++) {
                if (isValidVector(dx, dy)) {
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    private int findMaxHeight(int dx, int dy) {
        var x = 0;
        var y = 0;
        var maxHeight = Integer.MIN_VALUE;
        var hitTarget = false;
        for (var step = 0; step < maxSteps; step++) {
            x += dx;
            y += dy;
            dx = Math.max(0, dx - 1);
            dy--;
            maxHeight = Math.max(y, maxHeight);
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                hitTarget = true;
                break;
            }
        }
        return hitTarget ? maxHeight : Integer.MIN_VALUE;
    }

    private boolean isValidVector(int dx, int dy) {
        var x = 0;
        var y = 0;
        for (var step = 0; step < maxSteps; step++) {
            x += dx;
            y += dy;
            dx = Math.max(0, dx - 1);
            dy--;
            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                return true;
            }
        }
        return false;
    }
}