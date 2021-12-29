package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle24 extends BasePuzzleTest {
    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle24(getStoredInput(24));
        assertEquals(puzzle.solvePart1(), "91398299697996");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle24(getStoredInput(24));
        assertEquals(puzzle.solvePart2(), "41171183141291");
    }
}