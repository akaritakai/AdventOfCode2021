package net.akaritakai.aoc2021.ocr;

import java.util.HashMap;
import java.util.Map;

public class LetterOcr {
    private static final int LETTER_WIDTH = 4;
    private static final int LETTER_HEIGHT = 6;

    private static final Map<Character, boolean[][]> LETTERS = new HashMap<>();
    static {
        LETTERS.put('A', new boolean[][] {
                {false, true, true, false},
                {true, false, false, true},
                {true, false, false, true},
                {true, true, true, true},
                {true, false, false, true},
                {true, false, false, true}
        });
        LETTERS.put('B', new boolean[][] {
                {true, true, true, false},
                {true, false, false, true},
                {true, true, true, false},
                {true, false, false, true},
                {true, false, false, true},
                {true, true, true, false}
        });
        LETTERS.put('C', new boolean[][] {
                {false, true, true, false},
                {true, false, false, true},
                {true, false, false, false},
                {true, false, false, false},
                {true, false, false, true},
                {false, true, true, false}
        });
        // Letter D is unknown
        LETTERS.put('E', new boolean[][] {
                {true, true, true, true},
                {true, false, false, false},
                {true, true, true, false},
                {true, false, false, false},
                {true, false, false, false},
                {true, true, true, true}
        });
        LETTERS.put('F', new boolean[][] {
                {true, true, true, true},
                {true, false, false, false},
                {true, true, true, false},
                {true, false, false, false},
                {true, false, false, false},
                {true, false, false, false}
        });
        LETTERS.put('G', new boolean[][] {
                {false, true, true, false},
                {true, false, false, true},
                {true, false, false, false},
                {true, false, true, true},
                {true, false, false, true},
                {false, true, true, true}
        });
        LETTERS.put('H', new boolean[][] {
                {true, false, false, true},
                {true, false, false, true},
                {true, true, true, true},
                {true, false, false, true},
                {true, false, false, true},
                {true, false, false, true}
        });
        LETTERS.put('I', new boolean[][] {
                {false, true, true, true},
                {false, false, true, false},
                {false, false, true, false},
                {false, false, true, false},
                {false, false, true, false},
                {false, true, true, true}
        });
        LETTERS.put('J', new boolean[][] {
                {false, false, true, true},
                {false, false, false, true},
                {false, false, false, true},
                {false, false, false, true},
                {true, false, false, true},
                {false, true, true, false}
        });
        LETTERS.put('K', new boolean[][] {
                {true, false, false, true},
                {true, false, true, false},
                {true, true, false, false},
                {true, false, true, false},
                {true, false, true, false},
                {true, false, false, true}
        });
        LETTERS.put('L', new boolean[][] {
                {true, false, false, false},
                {true, false, false, false},
                {true, false, false, false},
                {true, false, false, false},
                {true, false, false, false},
                {true, true, true, true}
        });
        // Letter M is unknown
        // Letter N is unknown
        LETTERS.put('O', new boolean[][] {
                {false, true, true, false},
                {true, false, false, true},
                {true, false, false, true},
                {true, false, false, true},
                {true, false, false, true},
                {false, true, true, false}
        });
        LETTERS.put('P', new boolean[][] {
                {true, true, true, false},
                {true, false, false, true},
                {true, false, false, true},
                {true, true, true, false},
                {true, false, false, false},
                {true, false, false, false}
        });
        // Letter Q is unknown
        LETTERS.put('R', new boolean[][] {
                {true, true, true, false},
                {true, false, false, true},
                {true, false, false, true},
                {true, true, true, false},
                {true, false, true, false},
                {true, false, false, true}
        });
        LETTERS.put('S', new boolean[][] {
                {false, true, true, true},
                {true, false, false, false},
                {true, false, false, false},
                {false, true, true, false},
                {false, false, false, true},
                {true, true, true, false}
        });
        // Letter T is unknown
        LETTERS.put('U', new boolean[][] {
                {true, false, false, true},
                {true, false, false, true},
                {true, false, false, true},
                {true, false, false, true},
                {true, false, false, true},
                {false, true, true, false}
        });
        // Letter V is unknown
        // Letter W is unknown
        // Letter X is unknown
        LETTERS.put('Y', new boolean[][] {
                {true, false, false, false},
                {true, false, false, false},
                {false, true, false, true},
                {false, false, true, false},
                {false, false, true, false},
                {false, false, true, false}
        });
        LETTERS.put('Z', new boolean[][] {
                {true, true, true, true},
                {false, false, false, true},
                {false, false, true, false},
                {false, true, false, false},
                {true, false, false, false},
                {true, true, true, true}
        });
    }

    private static Character parseLetter(boolean[][] image, int rowOffset, int colOffset) throws OcrException {
        for (var letterEntry : LETTERS.entrySet()) {
            var letter = letterEntry.getKey();
            var pattern = letterEntry.getValue();
            boolean allMatch = true;
            OUTER: for (var row = 0; row < LETTER_HEIGHT; row++) {
                for (var col = 0; col < LETTER_WIDTH; col++) {
                    try {
                        if (image[rowOffset + row][colOffset + col] != pattern[row][col]) {
                            allMatch = false;
                            break OUTER;
                        }
                    } catch (Exception e) {
                        throw new OcrException(String.format(
                                "Issue parsing letter at row = %s, col = %s. Issue encountered at row = %s, col = %s",
                                rowOffset, colOffset, rowOffset + row, colOffset + col), e);
                    }
                }
            }
            if (allMatch) {
                return letter;
            }
        }
        throw new OcrException(String.format("Unknown letter at row = %s, col = %s", rowOffset, colOffset));
    }

    public static String parse(boolean[][] image, int rowOffset, int colOffset) {
        try {
            var sb = new StringBuilder();
            for (var col = colOffset; image[0].length - col >= 4; col += 5) {
                sb.append(parseLetter(image, rowOffset, col));
            }
            return sb.toString();
        } catch (OcrException e) {
            var sb = new StringBuilder();
            sb.append('\n');
            for (var row = rowOffset; row < image.length; row++) {
                for (var col = colOffset; col < image[0].length; col++) {
                    if (image[row][col]) {
                        sb.append("▌");
                    } else {
                        sb.append(' ');
                    }
                }
                sb.append('\n');
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    public static String parse(boolean[][] image) {
        return parse(image, 0, 0);
    }
}