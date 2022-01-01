package net.akaritakai.aoc2021;

import java.util.Stack;

public class Puzzle24 extends AbstractPuzzle {
    public Puzzle24(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 24;
    }

    @Override
    public String solvePart1() {
        var input = getPuzzleInput().trim().split("\n");
        return String.valueOf(solveMax(extractEquations(input)));
    }

    @Override
    public String solvePart2() {
        var input = getPuzzleInput().trim().split("\n");
        return String.valueOf(solveMin(extractEquations(input)));
    }

    private long solveMax(Equation[] equations) {
        int[] digits = new int[14];
        for (var equation : equations) {
            if (equation.rhs.value < 0) {
                digits[equation.lhs.index] = (int) (9 + equation.rhs.value);
                digits[equation.rhs.index] = 9;
            } else {
                digits[equation.lhs.index] = 9;
                digits[equation.rhs.index] = (int) (9 - equation.rhs.value);
            }
        }
        var result = 0L;
        for (var i = 0; i < 14; i++) {
            result = result * 10 + digits[i];
        }
        return result;
    }

    private long solveMin(Equation[] equations) {
        int[] digits = new int[14];
        for (var equation : equations) {
            if (equation.rhs.value < 0) {
                digits[equation.lhs.index] = 1;
                digits[equation.rhs.index] = (int) (1 - equation.rhs.value);
            } else {
                digits[equation.lhs.index] = (int) (1 + equation.rhs.value);
                digits[equation.rhs.index] = 1;
            }
        }
        var result = 0L;
        for (var i = 0; i < 14; i++) {
            result = result * 10 + digits[i];
        }
        return result;
    }

    private Equation[] extractEquations(String[] input) {
        var equations = new Equation[7];
        var j = 0;
        var stack = new Stack<Expression>();
        for (var i = 0; i < 14; i++) {
            if (input[18 * i + 4].equals("div z 1")) {
                var value = Long.parseLong(input[18 * i + 15].split(" ")[2]);
                stack.push(new Expression(i, value));
            } else {
                var value = Long.parseLong(input[18 * i + 5].split(" ")[2]);
                equations[j++] = new Equation(new Expression(i, 0L),
                        new Expression(stack.peek().index, stack.pop().value + value));
            }
        }
        return equations;
    }

    private record Equation(Expression lhs, Expression rhs) {
    }

    private record Expression(int index, long value) {
    }
}