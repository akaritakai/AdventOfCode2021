package net.akaritakai.aoc2021;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BidirectionalDijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Collection;

public class Puzzle15 extends AbstractPuzzle {
    private final int height;
    private final int width;
    private final int[][] risks;

    public Puzzle15(String puzzleInput) {
        super(puzzleInput);
        var lines = getPuzzleInput().trim().split("\n");
        height = lines.length;
        width = lines[0].length();
        risks = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                risks[y][x] = lines[y].charAt(x) - '0';
            }
        }
    }

    @Override
    public int getDay() {
        return 15;
    }

    @Override
    public String solvePart1() {
        var graph = buildGraph(false);
        var start = new Point(0, 0);
        var end = new Point(width - 1, height - 1);
        return String.valueOf((int) new BidirectionalDijkstraShortestPath<>(graph).getPath(start, end).getWeight());
    }

    @Override
    public String solvePart2() {
        var graph = buildGraph(true);
        var start = new Point(0, 0);
        var end = new Point(5 * width - 1, 5 * height - 1);
        return String.valueOf((int) new BidirectionalDijkstraShortestPath<>(graph).getPath(start, end).getWeight());
    }

    private Graph<Point, DefaultWeightedEdge> buildGraph(boolean part2) {
        var graph = new DefaultDirectedWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        var maxX = part2 ? width * 5 : width;
        var maxY = part2 ? height * 5 : height;
        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var point = new Point(x, y);
                graph.addVertex(point);
            }
        }
        for (var y = 0; y < maxY; y++) {
            for (var x = 0; x < maxX; x++) {
                var point = new Point(x, y);
                var value = (risks[y % height][x % width] + x / width + y / height - 1) % 9 + 1;
                for (var adjacent : adjacent(point, part2)) {
                    var edge = graph.addEdge(adjacent, point);
                    graph.setEdgeWeight(edge, value);
                }
            }
        }
        return graph;
    }

    private Collection<Point> adjacent(Point point, boolean part2) {
        var points = new ArrayList<Point>(4);
        points.add(new Point(point.x, point.y - 1));
        points.add(new Point(point.x, point.y + 1));
        points.add(new Point(point.x - 1, point.y));
        points.add(new Point(point.x + 1, point.y));
        if (part2) {
            points.removeIf(p -> p.x < 0 || p.x >= width * 5 || p.y < 0 || p.y >= height * 5);
        } else {
            points.removeIf(p -> p.x < 0 || p.x >= width || p.y < 0 || p.y >= height);
        }
        return points;
    }

    private record Point(int x, int y) {
    }
}