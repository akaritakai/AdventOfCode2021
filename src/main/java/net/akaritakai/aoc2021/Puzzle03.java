package net.akaritakai.aoc2021;

import java.util.Collection;
import java.util.LinkedList;
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
        var report = getPuzzleInput().lines().collect(Collectors.toList());
        var length = report.get(0).length();
        var gamma = 0L;
        var epsilon = 0L;
        for (var i = 0; i < length; i++) {
            if (mostCommonValue(report, i) == '0') {
                gamma <<= 1;
                epsilon = (epsilon << 1) | 1;
            } else {
                gamma = (gamma << 1) | 1;
                epsilon <<= 1;
            }
        }
        return String.valueOf(gamma * epsilon);
    }

    @Override
    public String solvePart2() {
        var report = getPuzzleInput().lines().collect(Collectors.toList());
        var length = report.get(0).length();
        var oxygenValues = new LinkedList<>(report);
        for (var i = 0; i < length && oxygenValues.size() > 1; i++) {
            var j = i;
            var leastCommonValue = leastCommonValue(oxygenValues, j);
            oxygenValues.removeIf(line -> line.charAt(j) == leastCommonValue);
        }
        var co2Values = new LinkedList<>(report);
        for (var i = 0; i < length && co2Values.size() > 1; i++) {
            var j = i;
            var mostCommonValue = mostCommonValue(co2Values, j);
            co2Values.removeIf(line -> line.charAt(j) == mostCommonValue);
        }
        var oxygenRating = Long.parseLong(oxygenValues.getFirst(), 2);
        var co2Rating = Long.parseLong(co2Values.getFirst(), 2);
        return String.valueOf(oxygenRating * co2Rating);
    }

    private char mostCommonValue(Collection<String> report, int position) {
        return report.stream().filter(value -> value.charAt(position) == '0').count() > (report.size() / 2) ? '0' : '1';
    }

    private char leastCommonValue(Collection<String> report, int position) {
        return mostCommonValue(report, position) == '0' ? '1' : '0';
    }
}