package net.akaritakai.aoc2021;

import net.akaritakai.aoc2021.ocr.LetterOcr;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * In Day 13, we are given a list of 2D points and a series of folds to make along the X and Y axes. After making the
 * folds, we are left with a 2D image of letters that we then OCR.
 *
 * The rendered font in question is the same one that has been used in previous years of Advent of Code, and thus we can
 * read the letters simply by using the pixel lookup table in the {@link LetterOcr} class.
 */
public class Puzzle13 extends AbstractPuzzle {
    private final Set<Point> points;
    private final List<String[]> instructions;

    public Puzzle13(String puzzleInput) {
        super(puzzleInput);
        var parts = getPuzzleInput().split("\n\n");
        points = Arrays.stream(parts[0].split("\n"))
                .map(line -> new Point(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1])))
                .collect(Collectors.toSet());
        instructions = Arrays.stream(parts[1].split("\n"))
                .map(line -> line.replace("fold along ", "").split("="))
                .collect(Collectors.toList());
    }

    @Override
    public int getDay() {
        return 13;
    }

    @Override
    public String solvePart1() {
        var instruction = instructions.get(0);
        if (instruction[0].equals("x")) {
            return String.valueOf(foldX(points.stream(), Integer.parseInt(instruction[1])).count());
        } else {
            return String.valueOf(foldY(points.stream(), Integer.parseInt(instruction[1])).count());
        }
    }

    @Override
    public String solvePart2() {
        var points = this.points.stream();
        for (var instruction : instructions) {
            if (instruction[0].equals("x")) {
                points = foldX(points, Integer.parseInt(instruction[1]));
            } else {
                points = foldY(points, Integer.parseInt(instruction[1]));
            }
        }
        return LetterOcr.parse(toImage(points));
    }

    private Stream<Point> foldX(Stream<Point> points, int x) {
        return points.map(point -> {
            if (point.x <= x) {
                return point;
            } else {
                return new Point(2 * x - point.x, point.y);
            }
        }).distinct();
    }

    private Stream<Point> foldY(Stream<Point> points, int y) {
        return points.map(point -> {
            if (point.y <= y) {
                return point;
            } else {
                return new Point(point.x, 2 * y - point.y);
            }
        }).distinct();
    }

    private boolean[][] toImage(Stream<Point> points) {
        var collection = points.toList();
        var minX = collection.stream().mapToInt(Point::x).min().orElseThrow();
        var minY = collection.stream().mapToInt(Point::y).min().orElseThrow();
        var maxX = collection.stream().mapToInt(Point::x).max().orElseThrow();
        var maxY = collection.stream().mapToInt(Point::y).max().orElseThrow();
        var image = new boolean[maxY - minY + 1][maxX - minX + 1];
        for (var point : collection) {
            image[point.y - minY][point.x - minX] = true;
        }
        return image;
    }

    private record Point(int x, int y) {
    }
}