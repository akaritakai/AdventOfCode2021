package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle07 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle07("16,1,2,0,4,2,7,1,2,14");
        assertEquals(puzzle.solvePart1(), "37");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle07(getStoredInput(7));
        assertEquals(puzzle.solvePart1(), "356922");
    }

    @Test
    public void testPart1Example2() {
        var puzzle = new Puzzle07("16,1,2,0,4,2,7,1,2,14");
        assertEquals(puzzle.solvePart2(), "168");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle07(getStoredInput(7));
        assertEquals(puzzle.solvePart2(), "100347031");
    }
}