package net.akaritakai.aoc2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * In Day 14, we are given another counting problem. We are given a string and set of expansion rules. We are to apply
 * the rules a given number of times to our starting string, and find the frequency of the most common symbol in the
 * resulting string.
 *
 * The string grows so quickly that attempting to hold it in memory and performing string operations on it is
 * infeasible. Instead, we can count the frequency of 2-symbol sequences in the string, and after careful accounting,
 * return the size of the resultant string.
 */
public class Puzzle14 extends AbstractPuzzle {
    private final String template;
    private final Map<String, String> rules;

    public Puzzle14(String puzzleInput) {
        super(puzzleInput);
        var parts = getPuzzleInput().split("\n\n");
        template = parts[0];
        rules = Arrays.stream(parts[1].trim().split("\n"))
                .map(line -> line.split(" -> "))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    @Override
    public int getDay() {
        return 14;
    }

    @Override
    public String solvePart1() {
        var counter = makeCounter();
        for (var step = 0; step < 10; step++) {
            counter = doStep(counter);
        }
        return String.valueOf(score(counter));
    }

    @Override
    public String solvePart2() {
        var counter = makeCounter();
        for (var step = 0; step < 40; step++) {
            counter = doStep(counter);
        }
        return String.valueOf(score(counter));
    }

    private Map<String, Long> makeCounter() {
        var counter = new HashMap<String, Long>();
        for (var i = 0; i < template.length() - 1; i++) {
            counter.merge(template.substring(i, i + 2), 1L, Long::sum);
        }
        return counter;
    }

    private Map<String, Long> doStep(Map<String, Long> counter) {
        var next = new HashMap<String, Long>();
        counter.forEach((pair, value) -> {
            if (rules.containsKey(pair)) {
                var middle = rules.get(pair);
                next.merge(pair.charAt(0) + middle, value, Long::sum);
                next.merge(middle + pair.charAt(1), value, Long::sum);
            }
        });
        return next;
    }

    private long score(Map<String, Long> counter) {
        var map = new long[26];
        for (var entry : counter.entrySet()) {
            map[entry.getKey().charAt(0) - 'A'] += entry.getValue();
        }
        map[template.charAt(template.length() - 1) - 'A']++;
        var max = Arrays.stream(map).filter(i -> i > 0).max().orElseThrow();
        var min = Arrays.stream(map).filter(i -> i > 0).min().orElseThrow();
        return max - min;
    }
}