package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle09 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle09("""
                2199943210
                3987894921
                9856789892
                8767896789
                9899965678
                """);
        assertEquals(puzzle.solvePart1(), "15");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle09(getStoredInput(9));
        assertEquals(puzzle.solvePart1(), "550");
    }

    @Test
    public void testPart2Example2() {
        var puzzle = new Puzzle09("""
                2199943210
                3987894921
                9856789892
                8767896789
                9899965678
                """);
        assertEquals(puzzle.solvePart2(), "1134");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle09(getStoredInput(9));
        assertEquals(puzzle.solvePart2(), "1100682");
    }
}