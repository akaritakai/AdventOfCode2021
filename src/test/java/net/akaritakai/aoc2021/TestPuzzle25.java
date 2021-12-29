package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle25 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle25("""
                v...>>.vv>
                .vv>>.vv..
                >>.>v>...v
                >>v>>.>.v.
                v>v.vv.v..
                >.>>..v...
                .vv..>.>v.
                v.v..>>v.v
                ....v..v.>
                """);
        assertEquals(puzzle.solvePart1(), "58");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle25(getStoredInput(25));
        assertEquals(puzzle.solvePart1(), "300");
    }
}