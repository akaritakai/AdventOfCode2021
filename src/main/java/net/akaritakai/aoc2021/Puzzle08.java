package net.akaritakai.aoc2021;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * In Day 8, we are shown all 10 numbers on a seven-segment display and asked to find out how it has been mis-wired.
 * Being shown all 10 numbers is always enough information to deduce the correct wiring, and we do so in a deterministic
 * manner.
 */
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

    private static Function<String, Integer> solver(String[] patterns) {
        var digits = new String[10];
        // We can deduce '1', '4', '7', and '8' by their length
        digits[1] = deduceDigit(patterns, p -> p.length() == 2);
        digits[4] = deduceDigit(patterns, p -> p.length() == 4);
        digits[7] = deduceDigit(patterns, p -> p.length() == 3);
        digits[8] = deduceDigit(patterns, p -> p.length() == 7);
        // We can deduce '6' as it is the only number to have length 6 and share 1 value in common with '1'
        digits[6] = deduceDigit(patterns, p -> p.length() == 6 && p.chars().filter(x -> digits[1].indexOf(x) != -1).count() == 1);
        // We can deduce f as the intersection of '6' and '1'
        var f = deduceSegment(digits[1], x -> digits[6].indexOf(x) != -1);
        // We can deduce c as '1' set minus f
        var c = deduceSegment(digits[1], x -> x != f);
        // We can deduce '3' as it is the only number to have length 5 and contain both c and f
        digits[3] = deduceDigit(patterns, p -> p.length() == 5 && p.indexOf(c) != -1 && p.indexOf(f) != -1);
        // We can deduce '2' as it is the only number to have length 5 and share 2 values in common with '4'
        digits[2] = deduceDigit(patterns, p -> p.length() == 5 && p.chars().filter(x -> digits[4].indexOf(x) != -1).count() == 2);
        // We can deduce b as '4' set minus '3'
        var b = deduceSegment(digits[4], x -> digits[3].indexOf(x) == -1);
        // We can deduce '5' as it is the only number to have length 5 and contain b
        digits[5] = deduceDigit(patterns, p -> p.length() == 5 && p.indexOf(b) != -1);
        // We can deduce d as '4' set minus '1' set minus 'b'
        var d = deduceSegment(digits[4], x -> digits[1].indexOf(x) == -1 && x != b);
        // We can deduce '0' as it is the only number to have length 6 and not contain d
        digits[0] = deduceDigit(patterns, p -> p.length() == 6 && p.indexOf(d) == -1);
        // We can deduce '9' as it is the only number to have length 6 and contain both c and d
        digits[9] = deduceDigit(patterns, p -> p.length() == 6 && p.indexOf(c) != -1 && p.indexOf(d) != -1);
        return pattern -> {
            var sorted = sort(pattern);
            for (var i = 0; i < 10; i++) {
                if (sorted.equals(digits[i])) {
                    return i;
                }
            }
            throw new IllegalArgumentException("No digit found for pattern: " + pattern);
        };
    }

    private static char deduceSegment(String pattern, IntPredicate predicate) {
        return (char) pattern.chars().filter(predicate).findAny().orElseThrow();
    }

    private static String deduceDigit(String[] patterns, Predicate<String> predicate) {
        return Arrays.stream(patterns).filter(predicate).map(Puzzle08::sort).findAny().orElseThrow();
    }

    private static String sort(String s) {
        var tmp = s.toCharArray();
        Arrays.sort(tmp);
        return new String(tmp);
    }
}