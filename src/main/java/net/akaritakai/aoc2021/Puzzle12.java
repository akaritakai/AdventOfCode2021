package net.akaritakai.aoc2021;

import java.util.*;

/**
 * In Day 12, we are told to count all the possible paths that begin at the given start location and end at the given
 * end location, with some simple rules regarding visiting the same location more than once.
 *
 * The problem is amenable to simple recursion to count the paths.
 */
public class Puzzle12 extends AbstractPuzzle {
    private final int start;
    private final int end;
    private final boolean[] smallCaves;
    private final boolean[][] edges;
    private final int[] path;

    public Puzzle12(String puzzleInput) {
        super(puzzleInput);
        var edges = new HashMap<String, Set<String>>();
        for (var line : getPuzzleInput().split("\n")) {
            var parts = line.split("-");
            var from = parts[0];
            var to = parts[1];
            edges.computeIfAbsent(from, s -> new HashSet<>()).add(to);
            edges.computeIfAbsent(to, s -> new HashSet<>()).add(from);
        }
        var caves = edges.keySet().stream().sorted().toArray(String[]::new);
        this.edges = new boolean[caves.length][caves.length];
        for (var i = 0; i < caves.length; i++) {
            for (var j = 0; j < caves.length; j++) {
                this.edges[i][j] = edges.get(caves[i]).contains(caves[j]);
            }
        }
        start = Arrays.binarySearch(caves, "start");
        end = Arrays.binarySearch(caves, "end");
        smallCaves = new boolean[caves.length];
        for (var i = 0; i < caves.length; i++) {
            if (caves[i].charAt(0) >= 'a') {
                smallCaves[i] = true;
            }
        }
        path = new int[caves.length];
    }

    @Override
    public int getDay() {
        return 12;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(countPaths(start, true));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(countPaths(start, false));
    }

    private int countPaths(int cave, boolean seenTwice) {
        if (cave == end) {
            return 1;
        }
        if (smallCaves[cave] && path[cave] > 0) {
            if (seenTwice || cave == start) {
                return 0;
            }
            seenTwice = true;
        }
        path[cave]++;
        var count = 0;
        for (var next = 0; next < edges[cave].length; next++) {
            if (edges[cave][next]) {
                count += countPaths(next, seenTwice);
            }
        }
        path[cave]--;
        return count;
    }
}