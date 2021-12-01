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
        var depths = getPuzzleInput().lines().map(Integer::parseInt).toList();
        var count = 0;
        for (var i = 1; i < depths.size(); i++) {
            if (depths.get(i) > depths.get(i - 1)) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    @Override
    public String solvePart2() {
        var depths = getPuzzleInput().lines().map(Integer::parseInt).toList();
        var count = 0;
        var prevSum = depths.get(0) + depths.get(1) + depths.get(2);
        for (var i = 3; i < depths.size(); i++) {
            var sum = depths.get(i - 2) + depths.get(i - 1) + depths.get(i);
            if (sum > prevSum) {
                count++;
            }
            prevSum = sum;
        }
        return String.valueOf(count);
    }
}