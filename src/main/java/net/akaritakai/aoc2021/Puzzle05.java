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

    public record LineSegment(Point start, Point end) {
        private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

        public static LineSegment parse(String s) {
            var matcher = PATTERN.matcher(s);
            if (matcher.find()) {
                var x1 = Integer.parseInt(matcher.group(1));
                var y1 = Integer.parseInt(matcher.group(2));
                var x2 = Integer.parseInt(matcher.group(3));
                var y2 = Integer.parseInt(matcher.group(4));
                return new LineSegment(new Point(x1, y1), new Point(x2, y2));
            }
            throw new IllegalArgumentException("Not a line segment");
        }

        public boolean isVerticalOrHorizontal() {
            return (start.x == end.x) || (start.y == end.y);
        }

        public Set<Point> getPoints() {
            var points = new HashSet<Point>();
            if (start.x == end.x) { // Vertical line
                var min = Math.min(start.y, end.y);
                var max = Math.max(start.y, end.y);
                for (var y = min; y <= max; y++) {
                    points.add(new Point(start.x, y));
                }
            } else if (start.y == end.y) { // Horizontal line
                var min = Math.min(start.x, end.x);
                var max = Math.max(start.x, end.x);
                for (var x = min; x <= max; x++) {
                    points.add(new Point(x, start.y));
                }
            } else { // Diagonal line
                var start = this.start.x < this.end.x ? this.start : this.end;
                var end = this.start.x < this.end.x ? this.end : this.start;
                // Invariant: start.x < end.x
                var x = start.x;
                var y = start.y;
                if (start.y < end.y) { // Increasing y values over segment
                    while (x <= end.x && y <= end.y) {
                        points.add(new Point(x++, y++));
                    }
                } else { // Decreasing y values over segment
                    while (x <= end.x && y >= end.y) {
                        points.add(new Point(x++, y--));
                    }
                }
            }
            return points;
        }
    }

    private record Point(int x, int y) {
    }
}