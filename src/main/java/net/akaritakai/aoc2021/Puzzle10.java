package net.akaritakai.aoc2021;

import java.util.ArrayList;
import java.util.LinkedList;

public class Puzzle10 extends AbstractPuzzle {
    public Puzzle10(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 10;
    }

    @Override
    public String solvePart1() {
        var score = 0;
        for (var line : getPuzzleInput().split("\n")) {
            var stack = new LinkedList<Character>();
            for (var c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    stack.push(c);
                } else {
                    if (stack.isEmpty()) {
                        break;
                    } else if (c == ')' && stack.peek() == '('
                            || c == ']' && stack.peek() == '['
                            || c == '}' && stack.peek() == '{'
                            || c == '>' && stack.peek() == '<') {
                        stack.pop();
                    } else {
                        switch (c) {
                            case ')' -> score += 3;
                            case ']' -> score += 57;
                            case '}' -> score += 1197;
                            case '>' -> score += 25137;
                        }
                        break;
                    }
                }
            }
        }
        return String.valueOf(score);
    }

    @Override
    public String solvePart2() {
        var costs = new ArrayList<Long>();
        for (var line : getPuzzleInput().split("\n")) {
            var stack = new LinkedList<Character>();
            var corrupted = false;
            for (var c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    stack.push(c);
                } else {
                    if (stack.isEmpty()) {
                        break;
                    } else if (c == ')' && stack.peek() == '('
                            || c == ']' && stack.peek() == '['
                            || c == '}' && stack.peek() == '{'
                            || c == '>' && stack.peek() == '<') {
                        stack.pop();
                    } else {
                        corrupted = true;
                        break;
                    }
                }
            }
            if (!corrupted) {
                var cost = 0L;
                while (!stack.isEmpty()) {
                    cost *= 5;
                    switch (stack.pop()) {
                        case '(' -> cost += 1;
                        case '[' -> cost += 2;
                        case '{' -> cost += 3;
                        case '<' -> cost += 4;
                    }
                }
                costs.add(cost);
            }
        }
        costs.sort(Long::compareTo);
        return String.valueOf(costs.get(costs.size() / 2));
    }
}