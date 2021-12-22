package net.akaritakai.aoc2021;

import java.util.HashMap;
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
                var x1 = Integer.parseInt(matcher.group(2));
                var x2 = Integer.parseInt(matcher.group(3));
                var y1 = Integer.parseInt(matcher.group(4));
                var y2 = Integer.parseInt(matcher.group(5));
                var z1 = Integer.parseInt(matcher.group(6));
                var z2 = Integer.parseInt(matcher.group(7));
                instructions[i] = new CubeInstruction(new Cube(x1, x2, y1, y2, z1, z2), instruction);
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
            for (var entry : cubes.entrySet()) {
                var cube2 = entry.getKey();
                var x1 = Math.max(cube1.minX, cube2.minX);
                var x2 = Math.min(cube1.maxX, cube2.maxX);
                var y1 = Math.max(cube1.minY, cube2.minY);
                var y2 = Math.min(cube1.maxY, cube2.maxY);
                var z1 = Math.max(cube1.minZ, cube2.minZ);
                var z2 = Math.min(cube1.maxZ, cube2.maxZ);
                if (x1 <= x2 && y1 <= y2 && z1 <= z2) {
                    var cube = new Cube(x1, x2, y1, y2, z1, z2);
                    update.merge(cube, -entry.getValue(), Long::sum);
                }
            }
            if (instruction.on()) {
                update.merge(cube1, 1L, Long::sum);
            }
            update.forEach((cube, count) -> cubes.merge(cube, count, Long::sum));
        }
        var volume = 0L;
        for (var entry : cubes.entrySet()) {
            var cube = entry.getKey();
            long x = cube.maxX - cube.minX + 1;
            long y = cube.maxY - cube.minY + 1;
            long z = cube.maxZ - cube.minZ + 1;
            volume += x * y * z * entry.getValue();
        }
        return String.valueOf(volume);
    }

    private record CubeInstruction(Cube cube, boolean on) {
    }

    private record Cube(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        private boolean contains(int x, int y, int z) {
            return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
        }
    }
}