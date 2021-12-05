package net.akaritakai.aoc2021;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Puzzle05 extends AbstractPuzzle {
    public Puzzle05(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public String solvePart1() {
        var segments = getPuzzleInput().lines()
                .map(LineSegment::parse)
                .collect(Collectors.toList());
        var points = new HashMap<Point, Integer>();
        segments.stream()
                .filter(LineSegment::isVerticalOrHorizontal)
                .forEach(segment -> segment.getPoints().forEach(point -> points.merge(point, 1, Integer::sum)));
        var count = points.values().stream().filter(n -> n >= 2).count();
        return String.valueOf(count);
    }

    @Override
    public String solvePart2() {
        var segments = getPuzzleInput().lines()
                .map(LineSegment::parse)
                .collect(Collectors.toList());
        var points = new HashMap<Point, Integer>();
        segments.forEach(segment -> segment.getPoints().forEach(point -> points.merge(point, 1, Integer::sum)));
        var count = points.values().stream().filter(n -> n >= 2).count();
        return String.valueOf(count);
    }

    public record LineSegment(int x1, int y1, int x2, int y2) {
        private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

        public static LineSegment parse(String s) {
            var matcher = PATTERN.matcher(s);
            if (matcher.find()) {
                var x1 = Integer.parseInt(matcher.group(1));
                var y1 = Integer.parseInt(matcher.group(2));
                var x2 = Integer.parseInt(matcher.group(3));
                var y2 = Integer.parseInt(matcher.group(4));
                return new LineSegment(x1, y1, x2, y2);
            }
            throw new IllegalArgumentException("Not a line segment");
        }

        public boolean isVerticalOrHorizontal() {
            return x1 == x2 || y1 == y2;
        }

        public Set<Point> getPoints() {
            var points = new HashSet<Point>();
            var dx = Integer.signum(x2 - x1);
            var dy = Integer.signum(y2 - y1);
            var x = x1;
            var y = y1;
            while (x != x2 + dx || y != y2 + dy) {
                points.add(new Point(x, y));
                x += dx;
                y += dy;
            }
            return points;
        }
    }

    private record Point(int x, int y) {
    }
}