package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle15 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle15("""
                1163751742
                1381373672
                2136511328
                3694931569
                7463417111
                1319128137
                1359912421
                3125421639
                1293138521
                2311944581
                """);
        assertEquals(puzzle.solvePart1(), "40");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle15(getStoredInput(15));
        assertEquals(puzzle.solvePart1(), "458");
    }

    @Test
    public void testPart2Example2() {
        var puzzle = new Puzzle15("""
                1163751742
                1381373672
                2136511328
                3694931569
                7463417111
                1319128137
                1359912421
                3125421639
                1293138521
                2311944581
                """);
        assertEquals(puzzle.solvePart2(), "315");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle15(getStoredInput(15));
        assertEquals(puzzle.solvePart2(), "2800");
    }
}