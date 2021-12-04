package net.akaritakai.aoc2021;

import java.util.*;
import java.util.stream.Collectors;

public class Puzzle04 extends AbstractPuzzle {
    public Puzzle04(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public String solvePart1() {
        var scanner = new Scanner(getPuzzleInput());
        var numbers = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        var boards = new ArrayList<BingoBoard>();
        while (scanner.hasNext()) {
            boards.add(new BingoBoard(scanner));
        }
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
        var scanner = new Scanner(getPuzzleInput());
        var numbers = Arrays.stream(scanner.nextLine().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        var boards = new ArrayList<BingoBoard>();
        while (scanner.hasNext()) {
            boards.add(new BingoBoard(scanner));
        }
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
        private final int[][] board = new int[5][5];
        private final boolean[][] marks = new boolean[5][5];

        public BingoBoard(Scanner input) {
            for (var y = 0; y < 5; y++) {
                for (var x = 0; x < 5; x++) {
                    board[y][x] = input.nextInt();
                }
            }
        }

        public boolean hasWon() {
            return won;
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
                    if (!marks[i][j]) {
                        row = false;
                    }
                    if (!marks[j][i]) {
                        col = false;
                    }
                }
                if (row || col) {
                    won = true;
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