package net.akaritakai.aoc2021;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class Puzzle11 extends AbstractPuzzle {
    private int height;
    private int width;

    public Puzzle11(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 11;
    }

    @Override
    public String solvePart1() {
        var grid = inputToGrid();
        return String.valueOf(IntStream.range(0, 100).map(i -> doStep(grid)).sum());
    }

    @Override
    public String solvePart2() {
        var grid = inputToGrid();
        var step = 1;
        while (true) {
            var count = doStep(grid);
            if (count == height * width) {
                return String.valueOf(step);
            }
            step++;
        }
    }

    private int doStep(int[][] grid) {
        var numFlashed = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x]++;
            }
        }
        var flashed = new boolean[height][width];
        boolean anyFlashed;
        do {
            anyFlashed = false;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (grid[y][x] > 9 && !flashed[y][x]) {
                        flashed[y][x] = true;
                        anyFlashed = true;
                        numFlashed++;
                        for (var point : adjacent(x, y)) {
                            grid[point.y][point.x]++;
                        }
                    }
                }
            }
        } while (anyFlashed);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (flashed[y][x]) {
                    grid[y][x] = 0;
                }
            }
        }
        return numFlashed;
    }

    private Collection<Point> adjacent(int x, int y) {
        var adjacent = new ArrayList<Point>(8);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if ((dx != 0 || dy != 0) && inBounds(x + dx, y + dy)) {
                    adjacent.add(new Point(x + dx, y + dy));
                }
            }
        }
        return adjacent;
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private int[][] inputToGrid() {
        var lines = getPuzzleInput().split("\n");
        height = lines.length;
        width = lines[0].length();
        var grid = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = lines[y].charAt(x) - '0';
            }
        }
        return grid;
    }

    private record Point(int x, int y) {
    }
}