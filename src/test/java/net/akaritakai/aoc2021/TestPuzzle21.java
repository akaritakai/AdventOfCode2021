package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle21 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle21("""
                Player 1 starting position: 4
                Player 2 starting position: 8
                """);
        assertEquals(puzzle.solvePart1(), "739785");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle21(getStoredInput(21));
        assertEquals(puzzle.solvePart1(), "504972");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle21("""
                Player 1 starting position: 4
                Player 2 starting position: 8
                """);
        assertEquals(puzzle.solvePart2(), "444356092776315");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle21(getStoredInput(21));
        assertEquals(puzzle.solvePart2(), "446968027750017");
    }
}