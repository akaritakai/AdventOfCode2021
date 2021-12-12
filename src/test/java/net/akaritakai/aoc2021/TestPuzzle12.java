package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle12 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle12("""
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
                """);
        assertEquals(puzzle.solvePart1(), "10");
    }

    @Test
    public void testPart1Example2() {
        var puzzle = new Puzzle12("""
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
                """);
        assertEquals(puzzle.solvePart1(), "19");
    }

    @Test
    public void testPart1Example3() {
        var puzzle = new Puzzle12("""
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW
                """);
        assertEquals(puzzle.solvePart1(), "226");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle12(getStoredInput(12));
        assertEquals(puzzle.solvePart1(), "4338");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle12("""
                start-A
                start-b
                A-c
                A-b
                b-d
                A-end
                b-end
                """);
        assertEquals(puzzle.solvePart2(), "36");
    }

    @Test
    public void testPart2Example2() {
        var puzzle = new Puzzle12("""
                dc-end
                HN-start
                start-kj
                dc-start
                dc-HN
                LN-dc
                HN-end
                kj-sa
                kj-HN
                kj-dc
                """);
        assertEquals(puzzle.solvePart2(), "103");
    }

    @Test
    public void testPart2Example3() {
        var puzzle = new Puzzle12("""
                fs-end
                he-DX
                fs-he
                start-DX
                pj-DX
                end-zg
                zg-sl
                zg-pj
                pj-he
                RW-he
                fs-DX
                pj-RW
                zg-RW
                start-pj
                he-WI
                zg-he
                pj-fs
                start-RW
                """);
        assertEquals(puzzle.solvePart2(), "3509");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle12(getStoredInput(12));
        assertEquals(puzzle.solvePart2(), "114189");
    }
}