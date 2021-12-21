package net.akaritakai.aoc2021;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Puzzle19 extends AbstractPuzzle {
    private final Scanner[] scanners;

    public Puzzle19(String puzzleInput) {
        super(puzzleInput);
        scanners = Arrays.stream(puzzleInput.trim().split("\n\n")).map(Scanner::new).toArray(Scanner[]::new);
        solve();
    }

    @Override
    public int getDay() {
        return 19;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(scanners[0].beacons.size());
    }

    @Override
    public String solvePart2() {
        var maxDistance = 0;
        for (var i = 0; i < scanners.length; i++) {
            for (var j = i + 1; j < scanners.length; j++) {
                var point1 = scanners[i].position;
                var point2 = scanners[j].position;
                maxDistance = Math.max(maxDistance, point1.distance(point2));
            }
        }
        return String.valueOf(maxDistance);
    }

    private void solve() {
        scanners[0].position = new Point(0, 0, 0);
        var unsolved = new ArrayList<>(Arrays.asList(scanners).subList(1, scanners.length));
        while (unsolved.size() > 0) {
            unsolved.removeIf(this::findTranslation);
        }
    }

    private boolean findTranslation(Scanner scanner) {
        for (var rotation : ROTATIONS) {
            var translation = scanner.beacons.stream().map(rotation).collect(Collectors.toSet());
            var diffs = new HashMap<Point, Integer>();
            for (var point1 : scanners[0].beacons) {
                for (var point2 : translation) {
                    diffs.merge(point1.difference(point2), 1, Integer::sum);
                }
            }
            var diff = diffs.entrySet().stream()
                    .filter(e -> e.getValue() >= 12)
                    .map(Map.Entry::getKey)
                    .findAny()
                    .orElse(null);
            if (diff != null) {
                scanner.position = diff;
                for (var point : translation) {
                    scanners[0].beacons.add(point.add(diff));
                }
                return true;
            }
        }
        return false;
    }

    private static class Scanner {
        private final Set<Point> beacons = new HashSet<>();
        private Point position;

        private Scanner(String scannerLines) {
            var lines = scannerLines.split("\n");
            for (var i = 1; i < lines.length; i++) {
                var tokens = lines[i].split(",");
                var x = Integer.parseInt(tokens[0]);
                var y = Integer.parseInt(tokens[1]);
                var z = Integer.parseInt(tokens[2]);
                beacons.add(new Point(x, y, z));
            }
        }
    }

    private record Point(int x, int y, int z) {
        Point add(Point other) {
            return new Point(x + other.x, y + other.y, z + other.z);
        }

        Point difference(Point other) {
            return new Point(x - other.x, y - other.y, z - other.z);
        }

        int distance(Point other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private static final List<UnaryOperator<Point>> ROTATIONS = List.of(
            p -> new Point(-p.x, -p.y, p.z),
            p -> new Point(-p.x, -p.z, -p.y),
            p -> new Point(-p.x, p.y, -p.z),
            p -> new Point(-p.x, p.z, p.y),
            p -> new Point(-p.y, -p.x, -p.z),
            p -> new Point(-p.y, -p.z, p.x),
            p -> new Point(-p.y, p.x, p.z),
            p -> new Point(-p.y, p.z, -p.x),
            p -> new Point(-p.z, -p.x, p.y),
            p -> new Point(-p.z, -p.y, -p.x),
            p -> new Point(-p.z, p.x, -p.y),
            p -> new Point(-p.z, p.y, p.x),
            p -> new Point(p.x, -p.y, -p.z),
            p -> new Point(p.x, -p.z, p.y),
            p -> new Point(p.x, p.y, p.z),
            p -> new Point(p.x, p.y, p.z),
            p -> new Point(p.x, p.z, -p.y),
            p -> new Point(p.y, -p.x, p.z),
            p -> new Point(p.y, -p.z, -p.x),
            p -> new Point(p.y, p.x, -p.z),
            p -> new Point(p.y, p.z, p.x),
            p -> new Point(p.z, -p.x, -p.y),
            p -> new Point(p.z, -p.y, p.x),
            p -> new Point(p.z, p.x, p.y),
            p -> new Point(p.z, p.y, -p.x)
    );
}