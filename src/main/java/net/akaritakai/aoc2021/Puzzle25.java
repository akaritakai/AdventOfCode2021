package net.akaritakai.aoc2021;

import java.util.HashSet;
import java.util.Set;

public class Puzzle25 extends AbstractPuzzle {
    public Puzzle25(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 25;
    }

    @Override
    public String solvePart1() {
        var seaFloor = new SeaFloor(getPuzzleInput());
        var steps = 1;
        while (seaFloor.step()) {
            steps++;
        }
        return String.valueOf(steps);
    }

    @Override
    public String solvePart2() {
        return "Day 25 has no part 2";
    }

    private static class SeaFloor {
        private final Set<Point> eastCucumbers = new HashSet<>();
        private final Set<Point> southCucumbers = new HashSet<>();
        private final int height;
        private final int width;

        public SeaFloor(String input) {
            var lines = input.trim().split("\n");
            height = lines.length;
            width = lines[0].length();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    switch (lines[y].charAt(x)) {
                        case '>' -> eastCucumbers.add(new Point(x, y));
                        case 'v' -> southCucumbers.add(new Point(x, y));
                    }
                }
            }
        }

        private boolean step() {
            var moved = false;
            var newEastCucumbers = new HashSet<Point>();
            var newSouthCucumbers = new HashSet<Point>();
            for (var oldPoint : eastCucumbers) {
                var newPoint = new Point((oldPoint.x + 1) % width, oldPoint.y);
                if (!eastCucumbers.contains(newPoint) && !southCucumbers.contains(newPoint)) {
                    newEastCucumbers.add(newPoint);
                    moved = true;
                } else {
                    newEastCucumbers.add(oldPoint);
                }
            }
            eastCucumbers.clear();
            eastCucumbers.addAll(newEastCucumbers);
            for (var oldPoint : southCucumbers) {
                var newPoint = new Point(oldPoint.x, (oldPoint.y + 1) % height);
                if (!eastCucumbers.contains(newPoint) && !southCucumbers.contains(newPoint)) {
                    newSouthCucumbers.add(newPoint);
                    moved = true;
                } else {
                    newSouthCucumbers.add(oldPoint);
                }
            }
            southCucumbers.clear();
            southCucumbers.addAll(newSouthCucumbers);
            return moved;
        }
    }

    private record Point(int x, int y) {
    }
}