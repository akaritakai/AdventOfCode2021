package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle14 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle14("""
                NNCB
                                
                CH -> B
                HH -> N
                CB -> H
                NH -> C
                HB -> C
                HC -> B
                HN -> C
                NN -> C
                BH -> H
                NC -> B
                NB -> B
                BN -> B
                BB -> N
                BC -> B
                CC -> N
                CN -> C
                """);
        assertEquals(puzzle.solvePart1(), "1588");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle14(getStoredInput(14));
        assertEquals(puzzle.solvePart1(), "3247");
    }

    @Test
    public void testPart2Example2() {
        var puzzle = new Puzzle14("""
                NNCB
                                
                CH -> B
                HH -> N
                CB -> H
                NH -> C
                HB -> C
                HC -> B
                HN -> C
                NN -> C
                BH -> H
                NC -> B
                NB -> B
                BN -> B
                BB -> N
                BC -> B
                CC -> N
                CN -> C
                """);
        assertEquals(puzzle.solvePart2(), "2188189693529");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle14(getStoredInput(14));
        assertEquals(puzzle.solvePart2(), "4110568157153");
    }
}