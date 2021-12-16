package net.akaritakai.aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPuzzle16 extends BasePuzzleTest {
    @Test
    public void testSingleDigitInputParsing() {
        assertEquals(Puzzle16.parseInput("0"), List.of(false, false, false, false));
        assertEquals(Puzzle16.parseInput("1"), List.of(false, false, false, true));
        assertEquals(Puzzle16.parseInput("2"), List.of(false, false, true, false));
        assertEquals(Puzzle16.parseInput("3"), List.of(false, false, true, true));
        assertEquals(Puzzle16.parseInput("4"), List.of(false, true, false, false));
        assertEquals(Puzzle16.parseInput("5"), List.of(false, true, false, true));
        assertEquals(Puzzle16.parseInput("6"), List.of(false, true, true, false));
        assertEquals(Puzzle16.parseInput("7"), List.of(false, true, true, true));
        assertEquals(Puzzle16.parseInput("8"), List.of(true, false, false, false));
        assertEquals(Puzzle16.parseInput("9"), List.of(true, false, false, true));
        assertEquals(Puzzle16.parseInput("A"), List.of(true, false, true, false));
        assertEquals(Puzzle16.parseInput("B"), List.of(true, false, true, true));
        assertEquals(Puzzle16.parseInput("C"), List.of(true, true, false, false));
        assertEquals(Puzzle16.parseInput("D"), List.of(true, true, false, true));
        assertEquals(Puzzle16.parseInput("E"), List.of(true, true, true, false));
        assertEquals(Puzzle16.parseInput("F"), List.of(true, true, true, true));
    }

    @Test
    public void testDoubleDigitInputParsing() {
        assertEquals(Puzzle16.parseInput("00"), List.of(false, false, false, false, false, false, false, false));
        assertEquals(Puzzle16.parseInput("22"), List.of(false, false, true, false, false, false, true, false));
    }

    @Test
    public void testExamplePacket1() {
        var input = "D2FE28";
        var packet = Puzzle16.parsePacket(Puzzle16.parseInput(input));
        assertEquals(packet.version(), 6);
        assertEquals(packet.typeId(), 4);
        assertEquals(packet.payload(), 2021);
    }

    @Test
    public void testExamplePacket2() {
        var input = "38006F45291200";
        var packet = Puzzle16.parsePacket(Puzzle16.parseInput(input));
        assertEquals(packet.version(), 1);
        assertEquals(packet.typeId(), 6);
        assertFalse(packet.lengthTypeId());
        assertEquals(packet.payload(), 27);
        assertEquals(packet.subPackets().size(), 2);
        var packet1 = packet.subPackets().get(0);
        assertEquals(packet1.typeId(), 4);
        assertEquals(packet1.payload(), 10);
        var packet2 = packet.subPackets().get(1);
        assertEquals(packet2.typeId(), 4);
        assertEquals(packet2.payload(), 20);
    }

    @Test
    public void testExamplePacket3() {
        var input = "EE00D40C823060";
        var packet = Puzzle16.parsePacket(Puzzle16.parseInput(input));
        assertEquals(packet.version(), 7);
        assertEquals(packet.typeId(), 3);
        assertTrue(packet.lengthTypeId());
        assertEquals(packet.payload(), 3);
        assertEquals(packet.subPackets().size(), 3);
        var packet1 = packet.subPackets().get(0);
        assertEquals(packet1.typeId(), 4);
        assertEquals(packet1.payload(), 1);
        var packet2 = packet.subPackets().get(1);
        assertEquals(packet2.typeId(), 4);
        assertEquals(packet2.payload(), 2);
        var packet3 = packet.subPackets().get(2);
        assertEquals(packet3.typeId(), 4);
        assertEquals(packet3.payload(), 3);
    }

    @Test
    public void testExamplePacket4() {
        var input = "8A004A801A8002F478";
        var packet = Puzzle16.parsePacket(Puzzle16.parseInput(input));
        assertEquals(packet.version(), 4);
        assertNotEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 1);
        packet = packet.subPackets().get(0);
        assertEquals(packet.version(), 1);
        assertNotEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 1);
        packet = packet.subPackets().get(0);
        assertEquals(packet.version(), 5);
        assertNotEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 1);
        packet = packet.subPackets().get(0);
        assertEquals(packet.version(), 6);
        assertEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 0);
    }

    @Test
    public void testExamplePacket5() {
        var input = "620080001611562C8802118E34";
        var packet = Puzzle16.parsePacket(Puzzle16.parseInput(input));
        assertEquals(packet.version(), 3);
        assertNotEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 2);
        var packet1 = packet.subPackets().get(0);
        assertNotEquals(packet1.typeId(), 4);
        assertEquals(packet1.subPackets().size(), 2);
        assertTrue(packet1.subPackets().stream().allMatch(p -> p.typeId() == 4));
        assertTrue(packet1.subPackets().stream().allMatch(p -> p.subPackets().size() == 0));
        var packet2 = packet.subPackets().get(1);
        assertNotEquals(packet2.typeId(), 4);
        assertEquals(packet2.subPackets().size(), 2);
        assertTrue(packet2.subPackets().stream().allMatch(p -> p.typeId() == 4));
        assertTrue(packet2.subPackets().stream().allMatch(p -> p.subPackets().size() == 0));
    }

    @Test
    public void testExamplePacket6() {
        var input = "C0015000016115A2E0802F182340";
        var packet = Puzzle16.parsePacket(Puzzle16.parseInput(input));
        assertNotEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 2);
        var packet1 = packet.subPackets().get(0);
        assertNotEquals(packet1.typeId(), 4);
        assertEquals(packet1.subPackets().size(), 2);
        assertTrue(packet1.subPackets().stream().allMatch(p -> p.typeId() == 4));
        assertTrue(packet1.subPackets().stream().allMatch(p -> p.subPackets().size() == 0));
        var packet2 = packet.subPackets().get(1);
        assertNotEquals(packet2.typeId(), 4);
        assertEquals(packet2.subPackets().size(), 2);
        assertTrue(packet2.subPackets().stream().allMatch(p -> p.typeId() == 4));
        assertTrue(packet2.subPackets().stream().allMatch(p -> p.subPackets().size() == 0));
    }

    @Test
    public void testExamplePacket7() {
        var input = "A0016C880162017C3686B18A3D4780";
        var packet = Puzzle16.parsePacket(Puzzle16.parseInput(input));
        assertNotEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 1);
        packet = packet.subPackets().get(0);
        assertNotEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 1);
        packet = packet.subPackets().get(0);
        assertNotEquals(packet.typeId(), 4);
        assertEquals(packet.subPackets().size(), 5);
        assertTrue(packet.subPackets().stream().allMatch(p -> p.typeId() == 4));
        assertTrue(packet.subPackets().stream().allMatch(p -> p.subPackets().size() == 0));
    }

    @Test
    public void testPart1Example1() {
        var puzzle = new Puzzle16("8A004A801A8002F478");
        assertEquals(puzzle.solvePart1(), "16");
    }

    @Test
    public void testPart1Example2() {
        var puzzle = new Puzzle16("620080001611562C8802118E34");
        assertEquals(puzzle.solvePart1(), "12");
    }

    @Test
    public void testPart1Example3() {
        var puzzle = new Puzzle16("C0015000016115A2E0802F182340");
        assertEquals(puzzle.solvePart1(), "23");
    }

    @Test
    public void testPart1Example4() {
        var puzzle = new Puzzle16("A0016C880162017C3686B18A3D4780");
        assertEquals(puzzle.solvePart1(), "31");
    }

    @Test
    public void testSolvePart1() throws Exception {
        var puzzle = new Puzzle16(getStoredInput(16));
        assertEquals(puzzle.solvePart1(), "883");
    }

    @Test
    public void testPart2Example1() {
        var puzzle = new Puzzle16("C200B40A82");
        assertEquals(puzzle.solvePart2(), "3");
    }

    @Test
    public void testPart2Example2() {
        var puzzle = new Puzzle16("04005AC33890");
        assertEquals(puzzle.solvePart2(), "54");
    }

    @Test
    public void testPart2Example3() {
        var puzzle = new Puzzle16("880086C3E88112");
        assertEquals(puzzle.solvePart2(), "7");
    }

    @Test
    public void testPart2Example4() {
        var puzzle = new Puzzle16("CE00C43D881120");
        assertEquals(puzzle.solvePart2(), "9");
    }

    @Test
    public void testPart2Example5() {
        var puzzle = new Puzzle16("D8005AC2A8F0");
        assertEquals(puzzle.solvePart2(), "1");
    }

    @Test
    public void testPart2Example6() {
        var puzzle = new Puzzle16("F600BC2D8F");
        assertEquals(puzzle.solvePart2(), "0");
    }

    @Test
    public void testPart2Example7() {
        var puzzle = new Puzzle16("9C005AC2F8F0");
        assertEquals(puzzle.solvePart2(), "0");
    }

    @Test
    public void testPart2Example8() {
        var puzzle = new Puzzle16("9C0141080250320F1802104A08");
        assertEquals(puzzle.solvePart2(), "1");
    }

    @Test
    public void testSolvePart2() throws Exception {
        var puzzle = new Puzzle16(getStoredInput(16));
        assertEquals(puzzle.solvePart2(), "1675198555015");
    }
}