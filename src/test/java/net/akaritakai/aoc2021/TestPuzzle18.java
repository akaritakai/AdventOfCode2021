package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPuzzle18 extends BasePuzzleTest {
    @Test
    public void testSnailfishNumber1() {
        var value = "[1,2]";
        assertEquals(Puzzle18.parse(value).toString(), value);
    }

    @Test
    public void testSnailfishNumber2() {
        var value = "[[1,2],3]";
        assertEquals(Puzzle18.parse(value).toString(), value);
    }

    @Test
    public void testSnailfishNumber3() {
        var value = "[9,[8,7]]";
        assertEquals(Puzzle18.parse(value).toString(), value);
    }

    @Test
    public void testSnailfishNumber4() {
        var value = "[[1,9],[8,5]]";
        assertEquals(Puzzle18.parse(value).toString(), value);
    }

    @Test
    public void testSnailfishNumber5() {
        var value = "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]";
        assertEquals(Puzzle18.parse(value).toString(), value);
    }

    @Test
    public void testSnailfishNumber6() {
        var value = "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]";
        assertEquals(Puzzle18.parse(value).toString(), value);
    }

    @Test
    public void testSnailfishNumber7() {
        var value = "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]";
        assertEquals(Puzzle18.parse(value).toString(), value);
    }

    @Test
    public void testExplode1() {
        var value = Puzzle18.parse("[[[[[9,8],1],2],3],4]");
        assertTrue(value.explode());
        assertEquals(value.toString(), "[[[[0,9],2],3],4]");
    }

    @Test
    public void testExplode2() {
        var value = Puzzle18.parse("[7,[6,[5,[4,[3,2]]]]]");
        assertTrue(value.explode());
        assertEquals(value.toString(), "[7,[6,[5,[7,0]]]]");
    }

    @Test
    public void testExplode3() {
        var value = Puzzle18.parse("[[6,[5,[4,[3,2]]]],1]");
        assertTrue(value.explode());
        assertEquals(value.toString(), "[[6,[5,[7,0]]],3]");
    }

    @Test
    public void testExplode4() {
        var value = Puzzle18.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]");
        assertTrue(value.explode());
        assertEquals(value.toString(), "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
    }

    @Test
    public void testExplode5() {
        var value = Puzzle18.parse("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
        assertTrue(value.explode());
        assertEquals(value.toString(), "[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
    }

    @Test
    public void testSplit1() {
        var value = Puzzle18.parse("10");
        assertTrue(value.split());
        assertEquals(value.toString(), "[5,5]");
    }

    @Test
    public void testSplit2() {
        var value = Puzzle18.parse("11");
        assertTrue(value.split());
        assertEquals(value.toString(), "[5,6]");
    }

    @Test
    public void testSplit3() {
        var value = Puzzle18.parse("12");
        assertTrue(value.split());
        assertEquals(value.toString(), "[6,6]");
    }

    @Test
    public void testAdd1() {
        var left = Puzzle18.parse("[[[[4,3],4],4],[7,[[8,4],9]]]");
        var right = Puzzle18.parse("[1,1]");
        var node = new Puzzle18.Node(left, right);
        assertEquals(node.toString(), "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
    }

    @Test
    public void testReduce1() {
        var left = Puzzle18.parse("[[[[4,3],4],4],[7,[[8,4],9]]]");
        var right = Puzzle18.parse("[1,1]");
        var node = new Puzzle18.Node(left, right);
        node.reduce();
        assertEquals(node.toString(), "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
    }

    @Test
    public void testSum1() {
        assertEquals(Puzzle18.sum(
                Puzzle18.parse("[1,1]"),
                Puzzle18.parse("[2,2]"),
                Puzzle18.parse("[3,3]"),
                Puzzle18.parse("[4,4]")
        ).toString(), "[[[[1,1],[2,2]],[3,3]],[4,4]]");
    }

    @Test
    public void testSum2() {
        assertEquals(Puzzle18.sum(
                Puzzle18.parse("[1,1]"),
                Puzzle18.parse("[2,2]"),
                Puzzle18.parse("[3,3]"),
                Puzzle18.parse("[4,4]"),
                Puzzle18.parse("[5,5]")
        ).toString(), "[[[[3,0],[5,3]],[4,4]],[5,5]]");
    }

    @Test
    public void testSum3() {
        assertEquals(Puzzle18.sum(
                Puzzle18.parse("[1,1]"),
                Puzzle18.parse("[2,2]"),
                Puzzle18.parse("[3,3]"),
                Puzzle18.parse("[4,4]"),
                Puzzle18.parse("[5,5]"),
                Puzzle18.parse("[6,6]")
        ).toString(), "[[[[5,0],[7,4]],[5,5]],[6,6]]");
    }

    @Test
    public void testSum4() {
        assertEquals(Puzzle18.sum(
                Puzzle18.parse("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]"),
                Puzzle18.parse("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]"),
                Puzzle18.parse("[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]"),
                Puzzle18.parse("[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]"),
                Puzzle18.parse("[7,[5,[[3,8],[1,4]]]]"),
                Puzzle18.parse("[[2,[2,2]],[8,[8,1]]]"),
                Puzzle18.parse("[2,9]"),
                Puzzle18.parse("[1,[[[9,3],9],[[9,0],[0,7]]]]"),
                Puzzle18.parse("[[[5,[7,4]],7],1]"),
                Puzzle18.parse("[[[[4,2],2],6],[8,7]]")
        ).toString(), "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
    }

    @Test
    public void testMagnitude1() {
        var input = Puzzle18.parse("[9,1]");
        assertEquals(input.magnitude(), 29);
    }

    @Test
    public void testMagnitude2() {
        var input = Puzzle18.parse("[1,9]");
        assertEquals(input.magnitude(), 21);
    }

    @Test
    public void testMagnitude3() {
        var input = Puzzle18.parse("[[9,1],[1,9]]");
        assertEquals(input.magnitude(), 129);
    }

    @Test
    public void testMagnitude4() {
        var input = Puzzle18.parse("[[1,2],[[3,4],5]]");
        assertEquals(input.magnitude(), 143);
    }

    @Test
    public void testMagnitude5() {
        var input = Puzzle18.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        assertEquals(input.magnitude(), 1384);
    }

    @Test
    public void testMagnitude6() {
        var input = Puzzle18.parse("[[[[1,1],[2,2]],[3,3]],[4,4]]");
        assertEquals(input.magnitude(), 445);
    }

    @Test
    public void testMagnitude7() {
        var input = Puzzle18.parse("[[[[3,0],[5,3]],[4,4]],[5,5]]");
        assertEquals(input.magnitude(), 791);
    }


    @Test
    public void testMagnitude8() {
        var input = Puzzle18.parse("[[[[5,0],[7,4]],[5,5]],[6,6]]");
        assertEquals(input.magnitude(), 1137);
    }

    @Test
    public void testMagnitude9() {
        var input = Puzzle18.parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
        assertEquals(input.magnitude(), 3488);
    }

    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle18("""
                [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                [[[5,[2,8]],4],[5,[[9,9],0]]]
                [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                [[[[5,4],[7,7]],8],[[8,3],8]]
                [[9,3],[[9,9],[6,[4,9]]]]
                [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
                """);
        assertEquals(puzzle.solvePart1(), "4140");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle18(getStoredInput(18));
        assertEquals(puzzle.solvePart1(), "4323");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle18("""
                [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                [[[5,[2,8]],4],[5,[[9,9],0]]]
                [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                [[[[5,4],[7,7]],8],[[8,3],8]]
                [[9,3],[[9,9],[6,[4,9]]]]
                [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
                """);
        assertEquals(puzzle.solvePart2(), "3993");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle18(getStoredInput(18));
        assertEquals(puzzle.solvePart2(), "4749");
    }
}