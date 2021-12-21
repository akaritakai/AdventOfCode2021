package net.akaritakai.aoc2021;

import java.util.HashSet;
import java.util.Set;

public class Puzzle20 extends AbstractPuzzle {
    private final boolean[] rule = new boolean[512];
    private final Set<Point> pixels = new HashSet<>();

    public Puzzle20(String puzzleInput) {
        super(puzzleInput);
        var parts = getPuzzleInput().trim().split("\n\n");
        for (var i = 0; i < parts[0].length(); i++) {
            rule[i] = parts[0].charAt(i) == '#';
        }
        var imageLines = parts[1].split("\n");
        for (var y = 0; y < imageLines.length; y++) {
            for (var x = 0; x < imageLines[y].length(); x++) {
                if (imageLines[y].charAt(x) == '#') {
                    pixels.add(new Point(x, y));
                }
            }
        }
    }

    @Override
    public int getDay() {
        return 20;
    }

    @Override
    public String solvePart1() {
        var step = 0;
        var pixels = this.pixels;
        pixels = applyAlgorithm(pixels, step++);
        pixels = applyAlgorithm(pixels, step);
        return String.valueOf(pixels.size());
    }

    @Override
    public String solvePart2() {
        var step = 0;
        var pixels = this.pixels;
        while (step < 50) {
            pixels = applyAlgorithm(pixels, step++);
        }
        return String.valueOf(pixels.size());
    }

    private Set<Point> applyAlgorithm(Set<Point> pixels, int step) {
        var minX = pixels.stream().mapToInt(Point::x).min().orElse(0) - 3;
        var maxX = pixels.stream().mapToInt(Point::x).max().orElse(0) + 3;
        var minY = pixels.stream().mapToInt(Point::y).min().orElse(0) - 3;
        var maxY = pixels.stream().mapToInt(Point::y).max().orElse(0) + 3;
        var newPixels = new HashSet<Point>();
        for (var y = minY; y <= maxY; y++) {
            for (var x = minX; x <= maxX; x++) {
                var value = 0;
                for (var dy = -1; dy <= 1; dy++) {
                    for (var dx = -1; dx <= 1; dx++) {
                        value <<= 1;
                        if (pixels.contains(new Point(x + dx, y + dy)) == ruleImpliesPixelsAreOn(step)) {
                            value |= 1;
                        }
                    }
                }
                if (rule[value] == ruleImpliesPixelsAreOn(step + 1)) {
                    newPixels.add(new Point(x, y));
                }
            }
        }
        return newPixels;
    }

    private boolean ruleImpliesPixelsAreOn(int step) {
        if (rule[0] && !rule[511]) {
            return step % 2 == 0;
        }
        return !rule[0];
    }

    private record Point(int x, int y) {
    }
}