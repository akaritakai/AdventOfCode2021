package net.akaritakai.aoc2021;

import java.util.stream.Collectors;

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
        var x = 0;
        var y = 0;
        var instructions = getPuzzleInput().lines().collect(Collectors.toList());
        for (var instruction : instructions) {
            var command = instruction.split(" ")[0];
            var value = Integer.parseInt(instruction.split(" ")[1]);
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
        var x = 0;
        var y = 0;
        var aim = 0;
        var instructions = getPuzzleInput().lines().collect(Collectors.toList());
        for (var instruction : instructions) {
            var command = instruction.split(" ")[0];
            var value = Integer.parseInt(instruction.split(" ")[1]);
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