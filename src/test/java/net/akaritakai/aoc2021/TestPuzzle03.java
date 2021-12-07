package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle03 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle03("""
                00100
                11110
                10110
                10111
                10101
                01111
                00111
                11100
                10000
                11001
                00010
                01010
                """);
        assertEquals(puzzle.solvePart1(), "198");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle03(getStoredInput(3));
        assertEquals(puzzle.solvePart1(), "3885894");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle03("""
                00100
                11110
                10110
                10111
                10101
                01111
                00111
                11100
                10000
                11001
                00010
                01010
                """);
        assertEquals(puzzle.solvePart2(), "230");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle03(getStoredInput(3));
        assertEquals(puzzle.solvePart2(), "4375225");
    }
}