package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle01 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle01("""
                199
                200
                208
                210
                200
                207
                240
                269
                260
                263
                """);
        assertEquals(puzzle.solvePart1(), "7");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle01(getStoredInput(1));
        assertEquals(puzzle.solvePart1(), "1532");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle01("""
                199
                200
                208
                210
                200
                207
                240
                269
                260
                263
                """);
        assertEquals(puzzle.solvePart2(), "5");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle01(getStoredInput(1));
        assertEquals(puzzle.solvePart2(), "1571");
    }
}