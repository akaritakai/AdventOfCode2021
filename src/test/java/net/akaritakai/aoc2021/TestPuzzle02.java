package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle02 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle02("""
                forward 5
                down 5
                forward 8
                up 3
                down 8
                forward 2
                """);
        assertEquals(puzzle.solvePart1(), "150");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle02(getStoredInput(2));
        assertEquals(puzzle.solvePart1(), "1604850");
    }

    @Test
    public void testPart1Example2() {
        var puzzle = new Puzzle02("""
                forward 5
                down 5
                forward 8
                up 3
                down 8
                forward 2
                """);
        assertEquals(puzzle.solvePart2(), "900");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle02(getStoredInput(2));
        assertEquals(puzzle.solvePart2(), "1685186100");
    }
}