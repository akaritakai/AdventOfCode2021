package net.akaritakai.aoc2021;

import java.util.regex.Pattern;

public class Puzzle21 extends AbstractPuzzle {
    private final int start1;
    private final int start2;

    public Puzzle21(String puzzleInput) {
        super(puzzleInput);
        var pattern = Pattern.compile("Player \\d starting position: (\\d+)");
        var matcher = pattern.matcher(puzzleInput);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid puzzle input");
        }
        start1 = Integer.parseInt(matcher.group(1)) - 1;
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid puzzle input");
        }
        start2 = Integer.parseInt(matcher.group(1)) - 1;
    }

    @Override
    public int getDay() {
        return 21;
    }

    @Override
    public String solvePart1() {
        var die = 0;
        var score1 = 0;
        var score2 = 0;
        var player1 = start1;
        var player2 = start2;
        while (true) {
            var roll = die++ % 100 + die++ % 100 + die++ % 100 + 3;
            player1 = (player1 + roll) % 10;
            score1 += player1 + 1;
            if (score1 >= 1000) {
                return String.valueOf(score2 * die);
            }
            roll = die++ % 100 + die++ % 100 + die++ % 100 + 3;
            player2 = (player2 + roll) % 10;
            score2 += player2 + 1;
            if (score2 >= 1000) {
                return String.valueOf(score1 * die);
            }
        }
    }

    @Override
    public String solvePart2() {
        long[][][][] universes = new long[10][10][22][22];
        universes[start1][start2][0][0] = 1;
        for (var score1 = 0; score1 < 21; score1++) {
            for (var score2 = 0; score2 < 21; score2++) {
                for (var player1 = 0; player1 < 10; player1++) {
                    for (var player2 = 0; player2 < 10; player2++) {
                        for (var roll1 : DIRAC_ROLLS) {
                            var newPlayer1 = (player1 + roll1) % 10;
                            var newScore1 = Math.min(score1 + newPlayer1 + 1, 21);
                            if (newScore1 == 21) {
                                universes[newPlayer1][player2][newScore1][score2] += universes[player1][player2][score1][score2];
                            } else {
                                for (var roll2 : DIRAC_ROLLS) {
                                    var newPlayer2 = (player2 + roll2) % 10;
                                    var newScore2 = Math.min(score2 + newPlayer2 + 1, 21);
                                    universes[newPlayer1][newPlayer2][newScore1][newScore2] += universes[player1][player2][score1][score2];
                                }
                            }
                        }
                    }
                }
            }
        }
        var wins1 = 0L;
        var wins2 = 0L;
        for (var player1 = 0; player1 < 10; player1++) {
            for (var player2 = 0; player2 < 10; player2++) {
                for (var score = 0; score < 21; score++) {
                    wins1 += universes[player1][player2][21][score];
                    wins2 += universes[player1][player2][score][21];
                }
            }
        }
        return String.valueOf(Math.max(wins1, wins2));
    }

    private static final int[] DIRAC_ROLLS = new int[27];
    static {
        int i = 0;
        for (var j = 1; j <= 3; j++) {
            for (var k = 1; k <= 3; k++) {
                for (var l = 1; l <= 3; l++) {
                    DIRAC_ROLLS[i++] = j + k + l;
                }
            }
        }
    }
}