package net.akaritakai.aoc2021;

import java.util.*;

/**
 * In Day 12, we are told to count all the possible paths that begin at the given start location and end at the given
 * end location, with some simple rules regarding visiting the same location more than once.
 *
 * The problem is amenable to simple recursion to count the paths.
 */
public class Puzzle12 extends AbstractPuzzle {
    private final Map<String, Set<String>> edges = new HashMap<>();

    public Puzzle12(String puzzleInput) {
        super(puzzleInput);
        for (var line : getPuzzleInput().split("\n")) {
            var parts = line.split("-");
            var from = parts[0];
            var to = parts[1];
            edges.computeIfAbsent(from, s -> new HashSet<>()).add(to);
            edges.computeIfAbsent(to, s -> new HashSet<>()).add(from);
        }
    }

    @Override
    public int getDay() {
        return 12;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(countPaths("start", new LinkedList<>(), true));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(countPaths("start", new LinkedList<>(), false));
    }

    private int countPaths(String cave, LinkedList<String> path, boolean seenTwice) {
        if (cave.equals("end")) {
            return 1;
        }
        if (isSmallCave(cave) && path.contains(cave)) {
            if (seenTwice || cave.equals("start")) {
                return 0;
            }
            seenTwice = true;
        }
        path.addLast(cave);
        var count = 0;
        for (var next : edges.get(cave)) {
            count += countPaths(next, path, seenTwice);
        }
        path.removeLast();
        return count;
    }

    private static boolean isSmallCave(String cave) {
        return cave.charAt(0) >= 'a';
    }
}