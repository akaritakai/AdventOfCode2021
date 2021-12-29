package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle23 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle23("""
                #############
                #...........#
                ###B#C#B#D###
                  #A#D#C#A#
                  #########
                """);
        assertEquals(puzzle.solvePart1(), "12521");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle23(getStoredInput(23));
        assertEquals(puzzle.solvePart1(), "18282");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle23("""
                #############
                #...........#
                ###B#C#B#D###
                  #A#D#C#A#
                  #########
                """);
        assertEquals(puzzle.solvePart2(), "44169");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle23(getStoredInput(23));
        assertEquals(puzzle.solvePart2(), "50132");
    }
}