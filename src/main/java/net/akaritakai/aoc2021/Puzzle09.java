package net.akaritakai.aoc2021;

import java.util.*;
import java.util.stream.Collectors;

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
        return String.valueOf(findLowPoints()
                .stream()
                .mapToInt(point -> grid[point.y][point.x] + 1)
                .sum());
    }

    @Override
    public String solvePart2() {
        var basinSizes = new PriorityQueue<Integer>(Comparator.reverseOrder());
        var seen = new HashSet<Point>();
        for (var point : findLowPoints()) {
            var basinSize = 0;
            var queue = new LinkedList<Point>();
            queue.add(point);
            while (!queue.isEmpty()) {
                var current = queue.remove();
                if (!seen.add(current)) {
                    continue;
                }
                basinSize++;
                queue.addAll(adjacentRising(current));
            }
            basinSizes.add(basinSize);
        }
        return String.valueOf(basinSizes.remove() * basinSizes.remove() * basinSizes.remove());
    }

    private Collection<Point> findLowPoints() {
        var points = new ArrayList<Point>();
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                var point = new Point(x, y);
                if (adjacent(point).stream().mapToInt(p -> grid[p.y][p.x]).allMatch(p -> p > grid[point.y][point.x])) {
                    points.add(point);
                }
            }
        }
        return points;
    }

    private Collection<Point> adjacentRising(Point point) {
        return adjacent(point).stream()
                .filter(p -> grid[p.y][p.x] > grid[point.y][point.x])
                .filter(p -> grid[p.y][p.x] != 9)
                .collect(Collectors.toList());
    }

    private Collection<Point> adjacent(Point point) {
        var points = new ArrayList<Point>();
        points.add(new Point(point.x, point.y - 1));
        points.add(new Point(point.x, point.y + 1));
        points.add(new Point(point.x - 1, point.y));
        points.add(new Point(point.x + 1, point.y));
        points.removeIf(p -> !inBounds(p.x, p.y));
        return points;
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }

    private record Point(int x, int y) {
    }
}