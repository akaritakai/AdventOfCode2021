package net.akaritakai.aoc2021;

public class Puzzle01 extends AbstractPuzzle {
    public Puzzle01(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 1;
    }

    @Override
    public String solvePart1() {
        var depths = getPuzzleInput().lines().mapToInt(Integer::parseInt).toArray();
        var count = 0;
        for (var i = 1; i < depths.length; i++) {
            if (depths[i] > depths[i - 1]) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    @Override
    public String solvePart2() {
        var depths = getPuzzleInput().lines().mapToInt(Integer::parseInt).toArray();
        var count = 0;
        var prevSum = depths[0] + depths[1] + depths[2];
        for (var i = 3; i < depths.length; i++) {
            var sum = depths[i - 2] + depths[i - 1] + depths[i];
            if (sum > prevSum) {
                count++;
            }
            prevSum = sum;
        }
        return String.valueOf(count);
    }
}