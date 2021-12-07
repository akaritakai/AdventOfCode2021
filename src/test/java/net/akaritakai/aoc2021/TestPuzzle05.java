package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle05 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle05("""
                0,9 -> 5,9
                8,0 -> 0,8
                9,4 -> 3,4
                2,2 -> 2,1
                7,0 -> 7,4
                6,4 -> 2,0
                0,9 -> 2,9
                3,4 -> 1,4
                0,0 -> 8,8
                5,5 -> 8,2
                """);
        assertEquals(puzzle.solvePart1(), "5");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle05(getStoredInput(5));
        assertEquals(puzzle.solvePart1(), "6113");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle05("""
                0,9 -> 5,9
                8,0 -> 0,8
                9,4 -> 3,4
                2,2 -> 2,1
                7,0 -> 7,4
                6,4 -> 2,0
                0,9 -> 2,9
                3,4 -> 1,4
                0,0 -> 8,8
                5,5 -> 8,2
                """);
        assertEquals(puzzle.solvePart2(), "12");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle05(getStoredInput(5));
        assertEquals(puzzle.solvePart2(), "20373");
    }
}