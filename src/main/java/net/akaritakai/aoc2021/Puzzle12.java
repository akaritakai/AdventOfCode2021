package net.akaritakai.aoc2021;

import java.util.*;

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
        return String.valueOf(countPaths("start", new HashMap<>(), false));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(countPaths("start", new HashMap<>(), true));
    }

    private int countPaths(String cave, Map<String, Integer> visited, boolean part2) {
        if (cave.equals("end")) {
            return 1;
        }
        if (!visitAllowed(cave, visited, part2)) {
            return 0;
        }
        visited.merge(cave, 1, Integer::sum);
        var count = 0;
        for (var adjacent : edges.getOrDefault(cave, Set.of())) {
            count += countPaths(adjacent, visited, part2);
        }
        visited.merge(cave, -1, Integer::sum);
        return count;
    }

    private boolean visitAllowed(String cave, Map<String, Integer> visited, boolean part2) {
        return !isSmallCave(cave)
                || (!cave.equals("start") || visited.getOrDefault(cave, 0) != 1)
                && (visited.getOrDefault(cave, 0) == 0
                || part2 && visited.entrySet().stream().noneMatch(e -> isSmallCave(e.getKey()) && e.getValue() > 1));
    }

    private boolean isSmallCave(String cave) {
        return cave.matches("^[a-z]+$");
    }
}