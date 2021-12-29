package net.akaritakai.aoc2021;

import java.util.HashSet;
import java.util.PriorityQueue;

import static net.akaritakai.aoc2021.Puzzle23.Amphipod.*;

public class Puzzle23 extends AbstractPuzzle {
    public Puzzle23(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 23;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(findMinCost(false));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(findMinCost(true));
    }

    private int findMinCost(boolean part2) {
        var seen = new HashSet<Long>();
        var end = State.end(part2).encode();
        var queue = new PriorityQueue<SearchNode>();
        queue.add(new SearchNode(new State(getPuzzleInput(), part2).encode(), 0));
        while (!queue.isEmpty()) {
            var node = queue.remove();
            if (!seen.add(node.state)) {
                continue;
            }
            if (node.state == end) {
                return node.cost;
            }
            var state = new State(node.state, part2);
            var cost = node.cost;

            // Find all possible moves from room to hallway
            for (var from = 11; from < state.size(); from++) {
                if (state.isUnmovable(from)) {
                    continue;
                }
                for (var to = 0; to <= 10; to++) {
                    if (state.isInvalidDestination(from, to)) {
                        continue;
                    }
                    if (state.isRoomToHallwayClear(from, to)) {
                        var energy = state.costFromRoomToHallway(from, to, state.state[from].energy());
                        queue.add(new SearchNode(state.next(from, to).encode(), cost + energy));
                    }
                }
            }

            // Find all possible moves from hallway to room
            for (var from = 0; from <= 10; from++) {
                if (state.isUnmovable(from)) {
                    continue;
                }
                for (var to = 11; to < state.size(); to++) {
                    if (state.isInvalidDestination(from, to)) {
                        continue;
                    }
                    if (state.isHallwayToRoomClear(from, to)) {
                        var energy = state.costFromHallwayToRoom(from, to, state.state[from].energy());
                        queue.add(new SearchNode(state.next(from, to).encode(), cost + energy));
                    }
                }
            }
        }
        throw new IllegalStateException("No solution found");
    }

    private record SearchNode(long state, int cost) implements Comparable<SearchNode> {
        public int compareTo(SearchNode other) {
            return Integer.compare(cost, other.cost);
        }
    }

    private static class State {
        Amphipod[] state;

        private State(Amphipod[] state) {
            this.state = state;
        }

        private State(long hash, boolean part2) {
            state = part2 ? new Amphipod[27] : new Amphipod[19];
            for (var i = state.length - 1; i >= 0; i--) {
                if (hash == 0) {
                    break;
                }
                var mod5 = (int) (hash % 5);
                if (mod5 != 0) {
                    state[i] = Amphipod.values()[mod5 - 1];
                }
                hash /= 5;
            }
        }

        private State(String input, boolean part2) {
            state = part2 ? new Amphipod[27] : new Amphipod[19];
            var j = 0;
            for (var i = 0; i < input.length(); i++) {
                var c = input.charAt(i);
                switch (c) {
                    case '.' -> state[j++] = null;
                    case 'A' -> state[j++] = AMBER;
                    case 'B' -> state[j++] = BRONZE;
                    case 'C' -> state[j++] = COPPER;
                    case 'D' -> state[j++] = DESERT;
                }
            }
            if (part2) {
                System.arraycopy(state, 15, state, 23, 4);
                var extra = new Amphipod[] { DESERT, COPPER, BRONZE, AMBER, DESERT, BRONZE, AMBER, COPPER };
                System.arraycopy(extra, 0, state, 15, 8);
            }
        }

        private static State end(boolean part2) {
            var state = part2 ? new Amphipod[27] : new Amphipod[19];
            for (var i = 11; i < state.length; i++) {
                state[i] = Amphipod.values()[(i - 11) % 4];
            }
            return new State(state);
        }

        private State next(int from, int to) {
            var state = new Amphipod[this.state.length];
            System.arraycopy(this.state, 0, state, 0, state.length);
            var tmp = state[from];
            state[from] = state[to];
            state[to] = tmp;
            return new State(state);
        }

        int size() {
            return state.length;
        }

        long encode() {
            var hash = 0L;
            for (Amphipod amphipod : state) {
                hash = 5 * hash + (amphipod == null ? 0 : amphipod.ordinal() + 1);
            }
            return hash;
        }

        boolean isUnmovable(int from) {
            if (state[from] == null) {
                return true; // There needs to be an amphipod in the space we're moving from
            }
            if (from < 11) { // We can move to a hallway space
                return false;
            }
            // An amphipod in a room might be in its final position, in which case we shouldn't move it
            if ((from - 11) % 4 != state[from].ordinal()) {
                return false; // The amphipod is in a room that is not our destination room
            }
            // The amphipod is in its final position, so we need to check if everyone below it is in its final position
            for (var i = from + 4; i < state.length; i += 4) {
                if (state[i] != state[from]) {
                    return false; // There is an amphipod below that is not in its final position, so we need to move out of their way
                }
            }
            return true; // All amphipods below are in their final position, so we cannot move
        }

        boolean isInvalidDestination(int from, int to) {
            if (to == 2 || to == 4 || to == 6 || to == 8) {
                return true; // We cannot move to the hallway entrance/exit
            }
            if (state[to] != null) {
                return true; // We cannot move to a position that already has an amphipod
            }
            if (to < 11) {
                return false; // We can move to a hallway space
            }
            if ((to - 11) % 4 != state[from].ordinal()) {
                return true; // We cannot move to a room that is not our destination room
            }
            var best = 0;
            for (var i = (to - 11) % 4 + 11; i < state.length; i += 4) {
                if (state[i] == null) {
                    best = i;
                } else if (state[i] != state[from]) {
                    return true; // We cannot move into a room that has an amphipod of a different type
                }
            }
            return to != best;
        }

        boolean isHallwayToRoomClear(int from, int to) {
            if (state[to] != null) { // Verify that the location we're moving to is clear
                return false;
            }
            var tmpTo = to; // Find the top of the room column we're moving to
            while (tmpTo > 14) {
                tmpTo -= 4;
            }
            var direction = from < (2 * tmpTo - 20) ? 1 : -1; // The direction we move in order to get out of our own way
            return isRoomToHallwayClear(to, from + direction);
        }

        boolean isRoomToHallwayClear(int from, int to) {
            while (from > 14) { // If we're not on the top room (11-14), we need to move up
                from -= 4;
                if (state[from] != null) { // We need the rooms above us to be clear to move into the hallway
                    return false;
                }
            }
            // We are guaranteed that the hallway space outside the room is clear (no need to check it)
            return isHallwayClear(2 * from - 20, to); // Check that the space outside the door to the hallway is clear
        }

        boolean isHallwayClear(int from, int to) {
            var min = Math.min(from, to);
            var max = Math.max(from, to);
            for (var i = min; i <= max; i++) {
                if (state[i] != null) {
                    return false;
                }
            }
            return true;
        }

        int costFromRoomToHallway(int from, int to, int energy) {
            var distance = 1; // It takes us at least 1 distance to move from the top room to the hallway
            while (from > 14) { // If we're not on the top room, we need to move up
                from -= 4;
                distance++;
            }
            distance += Math.abs(to - (2 * from - 20));
            return distance * energy;
        }

        int costFromHallwayToRoom(int from, int to, int energy) {
            return costFromRoomToHallway(to, from, energy);
        }
    }

    enum Amphipod {
        AMBER,
        BRONZE,
        COPPER,
        DESERT;

        private int energy() {
            return (int) Math.pow(10, ordinal());
        }
    }
}