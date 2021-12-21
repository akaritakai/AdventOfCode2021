package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPuzzle17 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle17("target area: x=20..30, y=-10..-5");
        assertEquals(puzzle.solvePart1(), "45");
    }

    @Test
    public void testPart1Example2() {
        // Example where maximum height won't be reached by y * (|y| - 1) / 2 due to x restrictions
        var puzzle = new Puzzle17("target area: x=22..27, y=-10..-5");
        assertEquals(puzzle.solvePart1(), "1");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle17(getStoredInput(17));
        assertEquals(puzzle.solvePart1(), "7626");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle17("target area: x=20..30, y=-10..-5");
        assertEquals(puzzle.solvePart2(), "112");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle17(getStoredInput(17));
        assertEquals(puzzle.solvePart2(), "2032");
    }
}