package net.akaritakai.aoc2021;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

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
                    var inputs = Arrays.stream(parts[0].split(" ")).toArray(String[]::new);
                    var outputs = Arrays.stream(parts[1].split(" ")).toArray(String[]::new);
                    var patterns = Stream.concat(Arrays.stream(inputs), Arrays.stream(outputs)).toArray(String[]::new);
                    var solver = solver(patterns);
                    return Arrays.stream(outputs).map(solver).reduce(0, (a, b) -> a * 10 + b);
                })
                .sum());
    }

    private Function<String, Integer> solver(String[] patterns) {
        try (var context = new Context()) {
            var solver = context.mkSolver();

            // Define a 7x7 grid of possible wires
            var wires = new BoolExpr[7][7];
            for (var i = 'a'; i <= 'g'; i++) {
                for (var j = 'a'; j <= 'g'; j++) {
                    wires[i - 'a'][j - 'a'] = context.mkBoolConst(i + "_" + j);
                }
            }

            //  Every pre-image in the function has a mapping
            for (var i = 0; i < 7; i++) {
                solver.add(context.mkOr(wires[i]));
            }

            // The function is bijective
            for (var i = 0; i < 7; i++) {
                for (var j = 0; j < 7; j++) {
                    for (var k = 0; k < 7; k++) {
                        if (j == k) {
                            continue;
                        }
                        solver.add(context.mkImplies(wires[i][j], context.mkNot(wires[i][k])));
                        solver.add(context.mkImplies(wires[j][i], context.mkNot(wires[k][i])));
                    }
                }
            }

            for (var pattern : patterns) {
                if (pattern.length() == 2) {
                    // Can only be a '1' so the wires must map to 'c' and 'f'
                    for (var i = 0; i < 2; i++) {
                        var c = pattern.charAt(i) - 'a';
                        solver.add(context.mkOr(wires[c][2], wires[c][5]));
                    }
                } else if (pattern.length() == 3) {
                    // Can only be a '7' so the wires must map to 'a', 'c', and 'f'
                    for (var i = 0; i < 3; i++) {
                        var c = pattern.charAt(i) - 'a';
                        solver.add(context.mkOr(wires[c][0], wires[c][2], wires[c][5]));
                    }
                } else if (pattern.length() == 4) {
                    // Can only be a '4' so the wires must map to 'b', 'c', 'd', and 'f'
                    for (var i = 0; i < 4; i++) {
                        var c = pattern.charAt(i) - 'a';
                        solver.add(context.mkOr(wires[c][1], wires[c][2], wires[c][3], wires[c][5]));
                    }
                } else if (pattern.length() == 5) {
                    // Can be a '2', '3', or '5'
                    // Three of the letters must map to 'a', 'd', 'g'
                    var exprs = new BoolExpr[3][5];
                    for (var i = 0; i < 5; i++) {
                        var c = pattern.charAt(i) - 'a';
                        exprs[0][i] = wires[c][0];
                        exprs[1][i] = wires[c][3];
                        exprs[2][i] = wires[c][6];
                    }
                    solver.add(context.mkAnd(context.mkOr(exprs[0]), context.mkOr(exprs[1]), context.mkOr(exprs[2])));
                    // The remaining two could be one of 'ce', 'cf', or 'bf'
                    exprs = new BoolExpr[3][20];
                    var k = 0;
                    for (var i = 0; i < 5; i++) {
                        for (var j = 0; j < 5; j++) {
                            if (i == j) {
                                continue;
                            }
                            var c1 = pattern.charAt(i) - 'a';
                            var c2 = pattern.charAt(j) - 'a';
                            exprs[0][k] = context.mkAnd(wires[c1][1], wires[c2][5]);
                            exprs[1][k] = context.mkAnd(wires[c1][2], wires[c2][4]);
                            exprs[2][k] = context.mkAnd(wires[c1][2], wires[c2][5]);
                            k++;
                        }
                    }
                    solver.add(context.mkOr(context.mkOr(exprs[0]), context.mkOr(exprs[1]), context.mkOr(exprs[2])));
                } else if (pattern.length() == 6) {
                    // Can be a '0', '6', or '9'
                    // Four of the letters must map to 'a', 'b', 'f', and 'g'
                    var exprs = new BoolExpr[4][6];
                    for (var i = 0; i < 6; i++) {
                        var c = pattern.charAt(i) - 'a';
                        exprs[0][i] = wires[c][0];
                        exprs[1][i] = wires[c][1];
                        exprs[2][i] = wires[c][5];
                        exprs[3][i] = wires[c][6];
                    }
                    solver.add(context.mkAnd(context.mkOr(exprs[0]), context.mkOr(exprs[1]), context.mkOr(exprs[2]),
                            context.mkOr(exprs[3])));
                    // The remaining two could be one of 'ce', 'de', or 'cd'
                    exprs = new BoolExpr[3][30];
                    var k = 0;
                    for (var i = 0; i < 6; i++) {
                        for (var j = 0; j < 6; j++) {
                            if (i == j) {
                                continue;
                            }
                            var c1 = pattern.charAt(i) - 'a';
                            var c2 = pattern.charAt(j) - 'a';
                            exprs[0][k] = context.mkAnd(wires[c1][2], wires[c2][3]);
                            exprs[1][k] = context.mkAnd(wires[c1][2], wires[c2][4]);
                            exprs[2][k] = context.mkAnd(wires[c1][3], wires[c2][5]);
                            k++;
                        }
                    }
                    solver.add(context.mkOr(context.mkOr(exprs[0]), context.mkOr(exprs[1]), context.mkOr(exprs[2])));
                }
            }


            // Now solve!
            solver.check();
            var model = solver.getModel();
            var solution = new boolean[7][7];
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (model.eval(wires[i][j], true).isTrue()) {
                        solution[i][j] = true;
                    }
                }
            }

            // Return the digit by appling the pattern over the solution
            return pattern -> {
                var result = new char[pattern.length()];
                var i = 0;
                for (var c : pattern.toCharArray()) {
                    for (var j = 'a'; j <= 'g'; j++) {
                        if (solution[c - 'a'][j - 'a']) {
                            result[i++] = j;
                            break;
                        }
                    }
                }
                Arrays.sort(result);
                return switch (new String(result)) {
                    case "abcefg" -> 0;
                    case "cf" -> 1;
                    case "acdeg" -> 2;
                    case "acdfg" -> 3;
                    case "bcdf" -> 4;
                    case "abdfg" -> 5;
                    case "abdefg" -> 6;
                    case "acf" -> 7;
                    case "abcdefg" -> 8;
                    case "abcdfg" -> 9;
                    default -> throw new IllegalArgumentException("Invalid pattern: " + pattern);
                };
            };
        }
    }
}