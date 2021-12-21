package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPuzzle20 extends BasePuzzleTest {
    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle20("""
                ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
                                
                #..#.
                #....
                ##..#
                ..#..
                ..###
                """);
        assertEquals(puzzle.solvePart1(), "35");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle20(getStoredInput(20));
        assertEquals(puzzle.solvePart1(), "5597");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle20("""
                ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
                                
                #..#.
                #....
                ##..#
                ..#..
                ..###
                """);
        assertEquals(puzzle.solvePart2(), "3351");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle20(getStoredInput(20));
        assertEquals(puzzle.solvePart2(), "18723");
    }
}