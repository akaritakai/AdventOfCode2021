package net.akaritakai.aoc2021;

import com.google.common.annotations.VisibleForTesting;

import java.util.Stack;
import java.util.regex.Pattern;

public class Puzzle18 extends AbstractPuzzle {
    private static final Pattern PATTERN = Pattern.compile("(\\[|]|,|\\d+)");

    public Puzzle18(String puzzleInput) {
        super(puzzleInput);
    }

    @Override
    public int getDay() {
        return 18;
    }

    @Override
    public String solvePart1() {
        var nodes = getPuzzleInput().trim().lines().map(Puzzle18::parse).toArray(Node[]::new);
        return String.valueOf(sum(nodes).magnitude());
    }

    @Override
    public String solvePart2() {
        var nodeStrings = getPuzzleInput().trim().lines().toArray(String[]::new);
        var maxSum = 0;
        for (var i = 0; i < nodeStrings.length; i++) {
            for (var j = i + 1; j < nodeStrings.length; j++) {
                maxSum = Math.max(maxSum, sum(parse(nodeStrings[i]), parse(nodeStrings[j])).magnitude());
                maxSum = Math.max(maxSum, sum(parse(nodeStrings[j]), parse(nodeStrings[i])).magnitude());
            }
        }
        return String.valueOf(maxSum);
    }

    @VisibleForTesting
    static Node sum(Node... nodes) {
        var node = nodes[0];
        node.reduce();
        for (var i = 1; i < nodes.length; i++) {
            node = new Node(node, nodes[i]);
            node.reduce();
        }
        return node;
    }

    @VisibleForTesting
    static Node parse(String input) {
        var output = new Stack<Node>();
        var stack = new Stack<String>();
        var matcher = PATTERN.matcher(input);
        while (matcher.find()) {
            var token = matcher.group(1);
            switch (token) {
                case "[" -> stack.push(token);
                case "]" -> {
                    while (!stack.peek().equals("[")) {
                        var right = output.pop();
                        var left = output.pop();
                        output.push(new Node(left, right));
                        stack.pop();
                    }
                    stack.pop();
                }
                case "," -> {
                    while (!stack.isEmpty() && !stack.peek().equals("[")) {
                        var right = output.pop();
                        var left = output.pop();
                        output.push(new Node(left, right));
                        stack.pop();
                    }
                    stack.push(token);
                }
                default -> output.push(new Node(Integer.parseInt(token)));
            }
        }
        return output.pop();
    }

    @VisibleForTesting
    static class Node {
        private Node parent;
        private Node left;
        private Node right;
        private int value;

        private Node(int value) {
            this.value = value;
        }

        private Node(Node parent, int value) {
            this.parent = parent;
            this.value = value;
        }

        @VisibleForTesting
        Node(Node left, Node right) {
            this.parent = null;
            this.left = left;
            this.right = right;
            left.parent = this;
            right.parent = this;
        }

        @VisibleForTesting
        void reduce() {
            while (true) {
                if (!explode() && !split()) break;
            }
        }

        @VisibleForTesting
        boolean explode() {
            return explode(0);
        }

        private boolean explode(int depth) {
            if (left == null && right == null) {
                return false;
            } else if (depth >= 4 && isLeafPair()) {
                var leftAdjacent = leftAdjacent();
                var rightAdjacent = rightAdjacent();
                if (leftAdjacent != null) {
                    leftAdjacent.value += left.value;
                }
                if (rightAdjacent != null) {
                    rightAdjacent.value += right.value;
                }
                left = null;
                right = null;
                value = 0;
                return true;
            } else {
                assert left != null;
                return left.explode(depth + 1) || right.explode(depth + 1);
            }
        }

        @VisibleForTesting
        boolean split() {
            if (isLeaf() && value < 10) {
                return false;
            } else if (isLeaf()) {
                var left = new Node(this, value / 2);
                var right = new Node(this, value - left.value);
                this.left = left;
                this.right = right;
                this.value = 0;
                return true;
            } else {
                assert left != null;
                return left.split() || right.split();
            }
        }

        private Node leftAdjacent() {
            if (this.parent == null) {
                return null;
            } else if (parent.right == this) {
                var node = parent.left;
                while (!node.isLeaf()) {
                    node = node.right;
                }
                return node;
            } else {
                return parent.leftAdjacent();
            }
        }

        private Node rightAdjacent() {
            if (this.parent == null) {
                return null;
            } else if (parent.left == this) {
                var node = parent.right;
                while (!node.isLeaf()) {
                    node = node.left;
                }
                return node;
            } else {
                return parent.rightAdjacent();
            }
        }

        @VisibleForTesting
        int magnitude() {
            if (isLeaf()) {
                return value;
            } else {
                assert left != null;
                return 3 * left.magnitude() + 2 * right.magnitude();
            }
        }

        private boolean isLeafPair() {
            return left != null && right != null && left.isLeaf() && right.isLeaf();
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public String toString() {
            if (isLeaf()) {
                return String.valueOf(value);
            } else {
                return String.format("[%s,%s]", left, right);
            }
        }
    }
}