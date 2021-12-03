package net.akaritakai.aoc2021;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Puzzle03 extends AbstractPuzzle {
    public Puzzle03(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public String solvePart1() {
        var lines = getPuzzleInput().lines().collect(Collectors.toList());
        var length = lines.get(0).length();
        var gammaSb = new StringBuilder();
        var epsilonSb = new StringBuilder();
        for (var i = 0; i < length; i++) {
            var zeros = 0;
            var ones = 0;
            for (var line : lines) {
                var c = line.charAt(i);
                if (c == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            if (zeros > ones) {
                gammaSb.append('0');
                epsilonSb.append('1');
            } else {
                gammaSb.append('1');
                epsilonSb.append('0');
            }
        }
        var gamma = Integer.parseInt(gammaSb.toString(), 2);
        var epsilon = Integer.parseInt(epsilonSb.toString(), 2);
        return String.valueOf(gamma * epsilon);
    }

    @Override
    public String solvePart2() {
        var lines = getPuzzleInput().lines().collect(Collectors.toList());
        var length = lines.get(0).length();
        var oxygenLines = new ArrayList<>(lines);
        for (var i = 0; i < length; i++) {
            if (oxygenLines.size() == 1) {
                break;
            }
            int zeros = 0;
            int ones = 0;
            for (var line : oxygenLines) {
                var c = line.charAt(i);
                if (c == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            if (zeros > ones) {
                int finalI = i;
                oxygenLines.removeIf(line -> line.charAt(finalI) == '1');
            } else {
                int finalI = i;
                oxygenLines.removeIf(line -> line.charAt(finalI) == '0');
            }
        }
        var co2Lines = new ArrayList<>(lines);
        for (var i = 0; i < length; i++) {
            if (co2Lines.size() == 1) {
                break;
            }
            int zeros = 0;
            int ones = 0;
            for (var line : co2Lines) {
                var c = line.charAt(i);
                if (c == '0') {
                    zeros++;
                } else {
                    ones++;
                }
            }
            if (zeros > ones) {
                int finalI = i;
                co2Lines.removeIf(line -> line.charAt(finalI) == '0');
            } else {
                int finalI = i;
                co2Lines.removeIf(line -> line.charAt(finalI) == '1');
            }
        }
        var oxygenRating = Integer.parseInt(oxygenLines.get(0), 2);
        var co2Rating = Integer.parseInt(co2Lines.get(0), 2);
        return String.valueOf(oxygenRating * co2Rating);
    }
}