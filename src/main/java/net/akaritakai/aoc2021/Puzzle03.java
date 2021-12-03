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
        var gamma = 0;
        var epsilon = 0;
        for (var i = 0; i < length; i++) {
            if (moreZeros(report, i)) {
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
            if (moreZeros(oxygenValues, i)) {
                oxygenValues.removeIf(line -> line.charAt(j) == '1');
            } else {
                oxygenValues.removeIf(line -> line.charAt(j) == '0');
            }
        }
        var co2Values = new LinkedList<>(report);
        for (var i = 0; i < length && co2Values.size() > 1; i++) {
            var j = i;
            if (moreZeros(co2Values, i)) {
                co2Values.removeIf(line -> line.charAt(j) == '0');
            } else {
                co2Values.removeIf(line -> line.charAt(j) == '1');
            }
        }
        var oxygenRating = Integer.parseInt(oxygenValues.getFirst(), 2);
        var co2Rating = Integer.parseInt(co2Values.getFirst(), 2);
        return String.valueOf(oxygenRating * co2Rating);
    }

    private boolean moreZeros(Collection<String> report, int position) {
        return report.stream().filter(value -> value.charAt(position) == '0').count() > (report.size() / 2);
    }
}