package net.akaritakai.aoc2021;

import java.util.*;

/**
 * In Day 9, we are given a height map of size m*n and asked to identify the basins in the map (both their low points
 * and their volume).
 *
 * Finding the low points can be done in O(m*n) time by checking that the adjacent cells are higher than the given cell.
 *
 * Finding the volume of a basin from a given low point can be done for all the cells in the basin also O(m*n) time via
 * the flood fill algorithm.
 */
public class Puzzle09 extends AbstractPuzzle {
    private final int[][] grid;
    private final int height;
    private final int width;

    public Puzzle09(String puzzleInput) {
        super(puzzleInput);
        var lines = getPuzzleInput().split("\n");
        height = lines.length;
        width = lines[0].length();
        grid = new int[height][width];
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                grid[y][x] = Integer.parseInt(lines[y].substring(x, x + 1));
            }
        }
    }

    @Override
    public int getDay() {
        return 9;
    }

    @Override
    public String solvePart1() {
        var lowPoints = findLowPoints();
        var sum = 0;
        for (var point : lowPoints) {
            sum += grid[point.y][point.x] + 1;
        }
        return String.valueOf(sum);
    }

    @Override
    public String solvePart2() {
        var basins = new int[3]; // Top 3 basin sizes
        var seen = new HashSet<Point>();
        for (var point : findLowPoints()) {
            var size = 0;
            var queue = new LinkedList<Point>();
            queue.add(point);
            while (!queue.isEmpty()) {
                var current = queue.remove();
                if (!seen.add(current)) {
                    continue;
                }
                size++;
                queue.addAll(adjacentRising(current));
            }
            if (size > basins[0]) {
                basins[0] = size;
                // 3-element sort
                if (basins[0] > basins[1]) {
                    swap(basins, 0, 1);
                }
                if (basins[1] > basins[2]) {
                    swap(basins, 1, 2);
                }
                if (basins[0] > basins[1]) {
                    swap(basins, 0, 1);
                }
            }
        }
        return String.valueOf(basins[0] * basins[1] * basins[2]);
    }

    private Collection<Point> findLowPoints() {
        var points = new ArrayList<Point>();
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                var isLowPoint = y == 0 || grid[y - 1][x] > grid[y][x]; // Up
                isLowPoint &= y == height - 1 || grid[y + 1][x] > grid[y][x]; // Down
                isLowPoint &= x == 0 || grid[y][x - 1] > grid[y][x]; // Left
                isLowPoint &= x == width - 1 || grid[y][x + 1] > grid[y][x]; // Right
                if (isLowPoint) {
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    private Collection<Point> adjacentRising(Point point) {
        var points = new ArrayList<Point>(4);
        var x = point.x;
        var y = point.y;
        // Up
        if (y > 0 && grid[y - 1][x] > grid[y][x] && grid[y - 1][x] != 9) {
            points.add(new Point(x, y - 1));
        }
        // Down
        if (y < height - 1 && grid[y + 1][x] > grid[y][x] && grid[y + 1][x] != 9) {
            points.add(new Point(x, y + 1));
        }
        // Left
        if (x > 0 && grid[y][x - 1] > grid[y][x] && grid[y][x - 1] != 9) {
            points.add(new Point(x - 1, y));
        }
        // Right
        if (x < width - 1 && grid[y][x + 1] > grid[y][x] && grid[y][x + 1] != 9) {
            points.add(new Point(x + 1, y));
        }
        return points;
    }

    private record Point(int x, int y) {
    }

    private static void swap(int[] array, int i, int j) {
        var tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}