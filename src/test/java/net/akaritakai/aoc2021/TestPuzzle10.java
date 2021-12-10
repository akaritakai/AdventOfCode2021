package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle10 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle10("""
                [({(<(())[]>[[{[]{<()<>>
                [(()[<>])]({[<{<<[]>>(
                {([(<{}[<>[]}>{[]{[(<()>
                (((({<>}<{<{<>}{[]{[]{}
                [[<[([]))<([[{}[[()]]]
                [{[{({}]{}}([{[{{{}}([]
                {<[[]]>}<{[{[{[]{()[[[]
                [<(<(<(<{}))><([]([]()
                <{([([[(<>()){}]>(<<{{
                <{([{{}}[<[[[<>{}]]]>[]]
                """);
        assertEquals(puzzle.solvePart1(), "26397");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle10(getStoredInput(10));
        assertEquals(puzzle.solvePart1(), "271245");
    }

    @Test
    public void testPart2Example2() {
        var puzzle = new Puzzle10("""
                [({(<(())[]>[[{[]{<()<>>
                [(()[<>])]({[<{<<[]>>(
                {([(<{}[<>[]}>{[]{[(<()>
                (((({<>}<{<{<>}{[]{[]{}
                [[<[([]))<([[{}[[()]]]
                [{[{({}]{}}([{[{{{}}([]
                {<[[]]>}<{[{[{[]{()[[[]
                [<(<(<(<{}))><([]([]()
                <{([([[(<>()){}]>(<<{{
                <{([{{}}[<[[[<>{}]]]>[]]
                """);
        assertEquals(puzzle.solvePart2(), "288957");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle10(getStoredInput(10));
        assertEquals(puzzle.solvePart2(), "1685293086");
    }
}