package net.akaritakai.aoc2021;

import java.util.Arrays;
import java.util.function.Function;

public class Puzzle08 extends AbstractPuzzle {
    public Puzzle08(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 8;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getPuzzleInput()
                .lines()
                .map(line -> line.split(" \\| ")[1])
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .filter(token -> token.length() == 2 || token.length() == 3 || token.length() == 4 || token.length() == 7)
                .count());
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getPuzzleInput().lines()
                .parallel()
                .mapToInt(line -> {
                    var parts = line.split(" \\| ");
                    var patterns = Arrays.stream(parts[0].split(" ")).toArray(String[]::new);
                    var outputs = Arrays.stream(parts[1].split(" ")).toArray(String[]::new);
                    var solver = solver(patterns);
                    return Arrays.stream(outputs).map(solver).reduce(0, (a, b) -> a * 10 + b);
                })
                .sum());
    }

    private Function<String, Integer> solver(String[] patterns) {
        var digits = new String[10];
        // We can deduce '1', '4', '7', and '8' by their length
        digits[1] = Arrays.stream(patterns).filter(p -> p.length() == 2).findAny().orElseThrow();
        digits[4] = Arrays.stream(patterns).filter(p -> p.length() == 4).findAny().orElseThrow();
        digits[7] = Arrays.stream(patterns).filter(p -> p.length() == 3).findAny().orElseThrow();
        digits[8] = Arrays.stream(patterns).filter(p -> p.length() == 7).findAny().orElseThrow();
        // We can deduce '6' as it is the only number to have length 6 and share 1 value in common with '1'
        digits[6] = Arrays.stream(patterns)
                .filter(p -> p.length() == 6 && p.chars().filter(x -> digits[1].indexOf(x) != -1).count() == 1)
                .findAny()
                .orElseThrow();
        // We can deduce f as the intersection of '6' and '1'
        var f = digits[1].chars().filter(x -> digits[6].indexOf(x) != -1).findAny().orElseThrow();
        // We can deduce c as '1' set minus f
        var c = digits[1].chars().filter(x -> x != f).findAny().orElseThrow();
        // We can deduce '3' as it is the only number to have length 5 and contain both c and f
        digits[3] = Arrays.stream(patterns).filter(p -> p.length() == 5 && p.indexOf(c) != -1 && p.indexOf(f) != -1)
                .findAny()
                .orElseThrow();
        // We can deduce '2' as it is the only number to have length 5 and share 2 values in common with '4'
        digits[2] = Arrays.stream(patterns)
                .filter(p -> p.length() == 5 && p.chars().filter(x -> digits[4].indexOf(x) != -1).count() == 2)
                .findAny()
                .orElseThrow();
        // We can deduce b as '4' set minus '3'
        var b = digits[4].chars().filter(x -> digits[3].indexOf(x) == -1).findAny().orElseThrow();
        // We can deduce '5' as it is the only number to have length 5 and contain b
        digits[5] = Arrays.stream(patterns).filter(p -> p.length() == 5 && p.indexOf(b) != -1).findAny().orElseThrow();
        // We can deduce d as '4' set minus '1' set minus 'b'
        var d = digits[4].chars().filter(x -> digits[1].indexOf(x) == -1).filter(x -> x != b)
                .findAny()
                .orElseThrow();
        // We can deduce '0' as it is the only number to have length 6 and not contain d
        digits[0] = Arrays.stream(patterns).filter(p -> p.length() == 6 && p.indexOf(d) == -1).findAny().orElseThrow();
        // We can deduce '9' as it is the only number to have length 6 and contain both c and d
        digits[9] = Arrays.stream(patterns).filter(p -> p.length() == 6 && p.indexOf(c) != -1 && p.indexOf(d) != -1)
                .findAny()
                .orElseThrow();
        // Sort the digits at the end (normalizing the order)
        for (var i = 0; i < 10; i++) {
            var result = digits[i].toCharArray();
            Arrays.sort(result);
            digits[i] = new String(result);
        }
        return pattern -> {
            var array = pattern.toCharArray();
            Arrays.sort(array);
            var sorted = new String(array);
            for (var i = 0; i < 10; i++) {
                if (sorted.equals(digits[i])) {
                    return i;
                }
            }
            throw new IllegalArgumentException("No digit found for pattern: " + pattern);
        };
    }
}