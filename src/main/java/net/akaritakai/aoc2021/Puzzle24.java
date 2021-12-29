package net.akaritakai.aoc2021;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;

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
        var solution = solve(extractEquations(input));
        return String.valueOf(solution.max);
    }

    @Override
    public String solvePart2() {
        var input = getPuzzleInput().trim().split("\n");
        var solution = solve(extractEquations(input));
        return String.valueOf(solution.min);
    }

    @SuppressWarnings("unchecked")
    private Pair solve(Equation[] equations) {
        try (var context = new Context()) {
            var optimizer = context.mkOptimize();
            var digits = new ArithExpr[14];
            for (var i = 0; i < 14; i++) {
                digits[i] = context.mkIntConst(String.valueOf((char) ('A' + i)));
                optimizer.Add(context.mkGe(digits[i], context.mkInt(1)));
                optimizer.Add(context.mkLe(digits[i], context.mkInt(9)));
            }
            for (var equation : equations) {
                var lhs = context.mkAdd(digits[equation.lhs.index], context.mkInt(equation.lhs.value));
                var rhs = context.mkAdd(digits[equation.rhs.index], context.mkInt(equation.rhs.value));
                optimizer.Add(context.mkEq(lhs, rhs));
            }
            var goal = context.mkIntConst("goal");
            var parts = new ArithExpr[14];
            for (var i = 0; i < 14; i++) {
                parts[i] = context.mkMul(digits[i], context.mkInt((long) Math.pow(10, 13 - i)));
            }
            optimizer.Add(context.mkEq(goal, context.mkAdd(parts)));
            optimizer.Push();
            optimizer.MkMaximize(goal);
            optimizer.Check();
            var max = Long.parseLong(optimizer.getModel().eval(goal, false).toString());
            optimizer.Pop();
            optimizer.MkMinimize(goal);
            optimizer.Check();
            var min = Long.parseLong(optimizer.getModel().eval(goal, false).toString());
            return new Pair(min, max);
        }
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

    private record Pair(long min, long max) {
    }
}