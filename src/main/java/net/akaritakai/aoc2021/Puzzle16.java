package net.akaritakai.aoc2021;

import com.google.common.annotations.VisibleForTesting;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * In Day 16, we are asked to make a protocol decoder.
 */
public class Puzzle16 extends AbstractPuzzle {
    public Puzzle16(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 16;
    }

    @Override
    public String solvePart1() {
        var packet = parsePacket(parseInput(getPuzzleInput()));
        return String.valueOf(versionSum(packet));
    }

    @Override
    public String solvePart2() {
        var packet = parsePacket(parseInput(getPuzzleInput()));
        return String.valueOf(evaluate(packet));
    }

    private static long versionSum(Packet packet) {
        var sum = packet.version;
        for (var subPacket : packet.subPackets) {
            sum += versionSum(subPacket);
        }
        return sum;
    }

    private static long evaluate(Packet packet) {
        return switch ((int) packet.typeId) {
            case 0 -> packet.subPackets.stream().mapToLong(Puzzle16::evaluate).sum();
            case 1 -> packet.subPackets.stream().mapToLong(Puzzle16::evaluate).reduce((a, b) -> a * b).orElseThrow();
            case 2 -> packet.subPackets.stream().mapToLong(Puzzle16::evaluate).min().orElseThrow();
            case 3 -> packet.subPackets.stream().mapToLong(Puzzle16::evaluate).max().orElseThrow();
            case 4 -> packet.payload;
            case 5 -> evaluate(packet.subPackets.get(0)) > evaluate(packet.subPackets.get(1)) ? 1L : 0L;
            case 6 -> evaluate(packet.subPackets.get(0)) < evaluate(packet.subPackets.get(1)) ? 1L : 0L;
            case 7 -> evaluate(packet.subPackets.get(0)) == evaluate(packet.subPackets.get(1)) ? 1L : 0L;
            default -> throw new IllegalArgumentException("Unknown typeId: " + packet.typeId);
        };
    }


    @VisibleForTesting
    static LinkedList<Boolean> parseInput(String input) {
        return input.trim().chars()
                .mapToObj(Character::toString)
                .mapToInt(c -> Integer.parseInt(c, 16))
                .mapToObj(c -> String.format("%4s", Integer.toBinaryString(c)).replace(' ', '0'))
                .collect(Collectors.joining())
                .chars()
                .mapToObj(c -> c == '1')
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @VisibleForTesting
    static Packet parsePacket(LinkedList<Boolean> input) {
        var version = (short) readBits(input, 3);
        var typeId = (short) readBits(input, 3);
        if (typeId == 4) {
            var payload = 0L;
            while (true) {
                var x = readBits(input, 5);
                payload <<= 4;
                payload |= (x & 15);
                if ((x & 16) == 0) {
                    break;
                }
            }
            return new Packet(version, typeId, false, payload, List.of());
        } else {
            var lengthTypeId = readBits(input, 1) == 1L;
            long payload;
            var subPackets = new LinkedList<Packet>();
            if (lengthTypeId) {
                payload = readBits(input, 11);
                for (var i = 0; i < payload; i++) {
                    subPackets.add(parsePacket(input));
                }
            } else {
                payload = readBits(input, 15);
                var payloadBits = new LinkedList<Boolean>();
                for (var i = 0; i < payload; i++) {
                    payloadBits.add(input.removeFirst());
                }
                while (payloadBits.size() > 0) {
                    subPackets.add(parsePacket(payloadBits));
                }
            }
            return new Packet(version, typeId, lengthTypeId, payload, subPackets);
        }
    }

    private static long readBits(LinkedList<Boolean> input, int size) {
        var result = 0L;
        for (var i = 0; i < size; i++) {
            result <<= 1;
            if (input.removeFirst()) {
                result |= 1;
            }
        }
        return result;
    }

    @VisibleForTesting
    record Packet(
            short version, // The packet version
            short typeId, // The packet type
            boolean lengthTypeId, // The length type id
            long payload, // The payload
            List<Packet> subPackets // Any sub-packets contained by this packet
    ) {
    }
}