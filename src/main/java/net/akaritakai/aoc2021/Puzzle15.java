package net.akaritakai.aoc2021;

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

        private int count;

        public int findMinRisk() {
            var comparator = new Comparator<int[]>() {
                @Override
                public int compare(int[] lhs, int[] rhs) {
                    return lhs[2] - rhs[2];
                }
            };
            var queue = new PriorityQueue<>(comparator);
            queue.add(new int[]{0, 0, dist[0][0] + width - 1 + height - 1});
            while (!queue.isEmpty()) {
                count++;
                var data = queue.poll();
                var x = data[0];
                var y = data[1];
                if (x == width - 1 && y == height - 1) {
                    return dist[y][x];
                }
                if (x > 0) {
                    var dx = x - 1;
                    var newDist = dist[y][x] + risk[y][dx];
                    if (newDist < dist[y][dx]) {
                        dist[y][dx] = newDist;
                        queue.add(new int[]{dx, y, newDist + Math.abs(dx - width + 1) + Math.abs(y - height + 1)});
                    }
                }
                if (y > 0) {
                    var dy = y - 1;
                    var newDist = dist[y][x] + risk[dy][x];
                    if (newDist < dist[dy][x]) {
                        dist[dy][x] = newDist;
                        queue.add(new int[]{x, dy, newDist + Math.abs(x - width + 1) + Math.abs(dy - height + 1)});
                    }
                }
                if (x < width - 1) {
                    var dx = x + 1;
                    var newDist = dist[y][x] + risk[y][dx];
                    if (newDist < dist[y][dx]) {
                        dist[y][dx] = newDist;
                        queue.add(new int[]{dx, y, newDist + Math.abs(dx - width + 1) + Math.abs(y - height + 1)});
                    }
                }
                if (y < height - 1) {
                    var dy = y + 1;
                    var newDist = dist[y][x] + risk[dy][x];
                    if (newDist < dist[dy][x]) {
                        dist[dy][x] = newDist;
                        queue.add(new int[]{x, dy, newDist + Math.abs(x - width + 1) + Math.abs(dy - height + 1)});
                    }
                }
            }
            throw new IllegalStateException("No path found");
        }
    }
}