package net.akaritakai.aoc2021;

import java.util.*;

/**
 * In Day 4, we are playing bingo with a given set of n numbers as draw numbers and k 5x5 boards.
 *
 * The naive solution solves the problem in O(n*k) time, and given the small values of n and k, there is little need to
 * optimize.
 */
public class Puzzle04 extends AbstractPuzzle {
    private final int[] numbers;
    private final List<BingoBoard> boards = new ArrayList<>();
    private boolean dirty;

    public Puzzle04(String puzzleInput) {
        super(puzzleInput);
        var scanner = new Scanner(getPuzzleInput());
        numbers = Arrays.stream(scanner.nextLine().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
        while (scanner.hasNext()) {
            var board = new int[5][5];
            for (var y = 0; y < 5; y++) {
                for (var x = 0; x < 5; x++) {
                    board[y][x] = scanner.nextInt();
                }
            }
            boards.add(new BingoBoard(board));
        }
    }

    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public String solvePart1() {
        if (dirty) {
            boards.forEach(BingoBoard::reset);
        }
        dirty = true;
        for (var number : numbers) {
            for (var board : boards) {
                board.addNumber(number);
                if (board.hasWon()) {
                    return String.valueOf(board.score());
                }
            }
        }
        throw new IllegalStateException("No bingo board won with the given input");
    }

    @Override
    public String solvePart2() {
        if (dirty) {
            boards.forEach(BingoBoard::reset);
        }
        dirty = true;
        for (var number : numbers) {
            for (var board : boards) {
                board.addNumber(number);
                if (boards.stream().allMatch(BingoBoard::hasWon)) {
                    return String.valueOf(board.score());
                }
            }
        }
        throw new IllegalStateException("Not all bingo board have won with the given input");
    }

    private static class BingoBoard {
        private boolean won = false;
        private int lastNumber = -1;
        private final int[][] board;
        private final boolean[][] marks = new boolean[5][5];

        public BingoBoard(int[][] board) {
            this.board = board;
        }

        public boolean hasWon() {
            return won;
        }

        public void reset() {
            won = false;
            for (var y = 0; y < 5; y++) {
                Arrays.fill(marks[y], false);
            }
        }

        public void addNumber(int n) {
            if (hasWon()) {
                return;
            }
            lastNumber = n;
            for (var y = 0; y < 5; y++) {
                for (var x = 0; x < 5; x++) {
                    if (board[y][x] == n) {
                        marks[y][x] = true;
                    }
                }
            }
            for (var i = 0; i < 5; i++) {
                boolean row = true;
                boolean col = true;
                for (var j = 0; j < 5; j++) {
                    row &= marks[i][j];
                    col &= marks[j][i];
                }
                if (row || col) {
                    won = true;
                    return;
                }
            }
        }

        public int score() {
            var sum = 0;
            for (var y = 0; y < 5; y++) {
                for (var x = 0; x < 5; x++) {
                    if (!marks[y][x]) {
                        sum += board[y][x];
                    }
                }
            }
            return sum * lastNumber;
        }
    }
}