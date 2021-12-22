package net.akaritakai.aoc2021;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class Puzzle22 extends AbstractPuzzle {
    private final CubeInstruction[] instructions;

    public Puzzle22(String puzzleInput) {
        super(puzzleInput);
        var lines = puzzleInput.trim().split("\n");
        instructions = new CubeInstruction[lines.length];
        var pattern = Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");
        for (var i = 0; i < lines.length; i++) {
            var line = lines[i];
            var matcher = pattern.matcher(line);
            if (matcher.matches()) {
                var instruction = matcher.group(1).equals("on");
                var minX = Integer.parseInt(matcher.group(2));
                var maxX = Integer.parseInt(matcher.group(3));
                var minY = Integer.parseInt(matcher.group(4));
                var maxY = Integer.parseInt(matcher.group(5));
                var minZ = Integer.parseInt(matcher.group(6));
                var maxZ = Integer.parseInt(matcher.group(7));
                instructions[i] = new CubeInstruction(new Cube(minX, maxX, minY, maxY, minZ, maxZ), instruction);
            }
        }
    }

    @Override
    public int getDay() {
        return 22;
    }

    @Override
    public String solvePart1() {
        var points = new boolean[101][101][101];
        for (var instruction : instructions) {
            for (var x = -50; x <= 50; x++) {
                for (var y = -50; y <= 50; y++) {
                    for (var z = -50; z <= 50; z++) {
                        if (instruction.cube().contains(x, y, z)) {
                            points[x + 50][y + 50][z + 50] = instruction.on();
                        }
                    }
                }
            }
        }
        var count = 0;
        for (var i = 0; i < 101; i++) {
            for (var j = 0; j < 101; j++) {
                for (var k = 0; k < 101; k++) {
                    if (points[i][j][k]) {
                        count++;
                    }
                }
            }
        }
        return String.valueOf(count);
    }

    @Override
    public String solvePart2() {
        var cubes = new HashMap<Cube, Long>();
        for (var instruction : instructions) {
            var cube1 = instruction.cube();
            var update = new HashMap<Cube, Long>();
            cubes.forEach((cube2, value) -> cube1.intersection(cube2).ifPresent(cube -> update.merge(cube, -value, Long::sum)));
            if (instruction.on()) {
                update.merge(cube1, 1L, Long::sum);
            }
            update.forEach((cube, count) -> cubes.merge(cube, count, Long::sum));
        }
        return String.valueOf(cubes.entrySet().stream().mapToLong(e -> e.getKey().volume() * e.getValue()).sum());
    }

    private record CubeInstruction(Cube cube, boolean on) {
    }

    private record Cube(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        private boolean contains(int x, int y, int z) {
            return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
        }
        private Optional<Cube> intersection(Cube other) {
            var minX = Math.max(this.minX, other.minX);
            var maxX = Math.min(this.maxX, other.maxX);
            var minY = Math.max(this.minY, other.minY);
            var maxY = Math.min(this.maxY, other.maxY);
            var minZ = Math.max(this.minZ, other.minZ);
            var maxZ = Math.min(this.maxZ, other.maxZ);
            if (minX > maxX || minY > maxY || minZ > maxZ) {
                return Optional.empty();
            }
            return Optional.of(new Cube(minX, maxX, minY, maxY, minZ, maxZ));
        }
        private long volume() {
            long x = maxX - minX + 1;
            long y = maxY - minY + 1;
            long z = maxZ - minZ + 1;
            return x * y * z;
        }
    }
}