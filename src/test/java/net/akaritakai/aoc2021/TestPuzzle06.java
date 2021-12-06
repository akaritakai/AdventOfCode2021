package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle06 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle06("3,4,3,1,2");
        assertEquals(puzzle.solvePart1(), "5934");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle06(getStoredInput(6));
        assertEquals(puzzle.solvePart1(), "349549");
    }

    @Test
    public void testPart1Example2() {
        var puzzle = new Puzzle06("3,4,3,1,2");
        assertEquals(puzzle.solvePart2(), "26984457539");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle06(getStoredInput(6));
        assertEquals(puzzle.solvePart2(), "1589590444365");
    }
}