package net.akaritakai.aoc2021;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * In Day 15, we are given a weighted, directed graph of n vertices and told to find the shortest path from a given
 * start and end vertex.
 *
 * Using Dijkstra's algorithm, we can find the shortest path in O(n log n) time.
 */
public class Puzzle15 extends AbstractPuzzle {
    public Puzzle15(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 15;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(new Graph(getPuzzleInput(), false).findMinRisk());
    }

    @Override
    public String solvePart2() {
        return String.valueOf(new Graph(getPuzzleInput(), true).findMinRisk());
    }

    private static class Graph {
        private final int[][] risk;
        private final int[][] dist;
        private final int height;
        private final int width;

        public Graph(String puzzleInput, boolean part2) {
            var lines = puzzleInput.trim().split("\n");
            var height = lines.length;
            var width = lines[0].length();
            var maxY = part2 ? height * 5 : height;
            var maxX = part2 ? width * 5 : width;
            risk = new int[maxY][maxX];
            dist = new int[maxY][maxX];
            for (var y = 0; y < height; y++) {
                for (var x = 0; x < width; x++) {
                    risk[y][x] = lines[y].charAt(x) - '0';
                }
            }
            for (var y = 0; y < maxY; y++) {
                for (var x = 0; x < maxX; x++) {
                    if (x == 0 && y == 0) {
                        dist[0][0] = 0;
                    } else {
                        dist[y][x] = 10 * maxX * maxY;
                    }
                    risk[y][x] = (risk[y % height][x % width] + x / width + y / height - 1) % 9 + 1;
                }
            }
            this.height = maxY;
            this.width = maxX;
        }

        public int findMinRisk() {
            var queue = new PriorityQueue<Point>(Comparator.comparingInt(p -> dist[p.y][p.x]));
            queue.add(new Point(0, 0));
            while (!queue.isEmpty()) {
                var point = queue.poll();
                if (point.x == width - 1 && point.y == height - 1) {
                    return dist[point.y][point.x];
                }
                for (var adjacent : adjacent(point)) {
                    var newDist = dist[point.y][point.x] + risk[adjacent.y][adjacent.x];
                    if (newDist < dist[adjacent.y][adjacent.x]) {
                        dist[adjacent.y][adjacent.x] = newDist;
                        queue.add(adjacent);
                    }
                }
            }
            throw new IllegalStateException("No path found");
        }

        private Collection<Point> adjacent(Point point) {
            var adjacent = new ArrayList<Point>(4);
            if (point.x > 0) {
                adjacent.add(new Point(point.x - 1, point.y));
            }
            if (point.y > 0) {
                adjacent.add(new Point(point.x, point.y - 1));
            }
            if (point.x < width - 1) {
                adjacent.add(new Point(point.x + 1, point.y));
            }
            if (point.y < height - 1) {
                adjacent.add(new Point(point.x, point.y + 1));
            }
            return adjacent;
        }
    }

    private record Point(int x, int y) {
    }
}