package net.akaritakai.aoc2021;

/**
 * In Day 2, we're given a list of movement instructions to parse and told to find the final position after applying
 * them in order. This is done in O(n).
 */
public class Puzzle02 extends AbstractPuzzle {
    private final Instruction[] instructions;

    public Puzzle02(String puzzleInput) {
        super(puzzleInput);
        instructions = getPuzzleInput().lines().map(Instruction::parse).toArray(Instruction[]::new);
    }

    @Override
    public int getDay() {
        return 2;
    }

    @Override
    public String solvePart1() {
        var x = 0L;
        var y = 0L;
        for (var instruction : instructions) {
            switch (instruction.command) {
                case "forward" -> x += instruction.value;
                case "down" -> y += instruction.value;
                case "up" -> y -= instruction.value;
            }
        }
        return String.valueOf(x * y);
    }

    @Override
    public String solvePart2() {
        var x = 0L;
        var y = 0L;
        var aim = 0L;
        for (var instruction : instructions) {
            switch (instruction.command) {
                case "forward" -> {
                    x += instruction.value;
                    y += aim * instruction.value;
                }
                case "down" -> aim += instruction.value;
                case "up" -> aim -= instruction.value;
            }
        }
        return String.valueOf(x * y);
    }

    private record Instruction(String command, long value) {
        public static Instruction parse(String instruction) {
            var tokens = instruction.split(" ");
            var command = tokens[0];
            var value = Long.parseLong(tokens[1]);
            return new Instruction(command, value);
        }
    }
}