package net.akaritakai.aoc2021;

/**
 * In Day 2, we're given a list of movement instructions to parse and told to find the final position after applying
 * them in order. This is done in O(n).
 */
public class Puzzle02 extends AbstractPuzzle {
    public Puzzle02(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 2;
    }

    @Override
    public String solvePart1() {
        var x = 0L;
        var y = 0L;
        var instructions = getPuzzleInput().lines().toArray(String[]::new);
        for (var instruction : instructions) {
            var tokens = instruction.split(" ");
            var command = tokens[0];
            var value = Long.parseLong(tokens[1]);
            switch (command) {
                case "forward" -> x += value;
                case "down" -> y += value;
                case "up" -> y -= value;
            }
        }
        return String.valueOf(x * y);
    }

    @Override
    public String solvePart2() {
        var x = 0L;
        var y = 0L;
        var aim = 0L;
        var instructions = getPuzzleInput().lines().toArray(String[]::new);
        for (var instruction : instructions) {
            var tokens = instruction.split(" ");
            var command = tokens[0];
            var value = Long.parseLong(tokens[1]);
            switch (command) {
                case "forward" -> {
                    x += value;
                    y += aim * value;
                }
                case "down" -> aim += value;
                case "up" -> aim -= value;
            }
        }
        return String.valueOf(x * y);
    }
}