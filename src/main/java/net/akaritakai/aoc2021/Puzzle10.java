package net.akaritakai.aoc2021;

import java.util.ArrayList;
import java.util.Stack;

/**
 * In Day 10, we are given a context-free grammar for a simple language of various kinds of balanced brackets and told
 * to identify the earliest location in which the parse is invalid or incomplete (and if incomplete, how to complete
 * it).
 *
 * This is achieved with a simple stack-based algorithm.
 */
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
            var stack = new Stack<Character>();
            for (var c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    stack.push(c);
                } else if (stack.isEmpty()) {
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
        return String.valueOf(score);
    }

    @Override
    public String solvePart2() {
        var costs = new ArrayList<Long>();
        for (var line : getPuzzleInput().split("\n")) {
            var corrupted = false;
            var stack = new Stack<Character>();
            for (var c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    stack.push(c);
                } else if (stack.isEmpty()) {
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