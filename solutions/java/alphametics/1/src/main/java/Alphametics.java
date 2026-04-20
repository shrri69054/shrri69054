import java.util.*;
import java.util.stream.IntStream;

public class Alphametics {
    private final char[][] expression;
    private final Map<Character, Integer> letterMap = new HashMap<>();

    public Alphametics(String text) {
        this.expression = makeExpression(text);

        for (int i = 0; i < expression.length; i++) {
            for (int j = 0; j < expression[i].length; j++) {
                char ch = expression[i][j];
                if (Character.isLetter(ch)) {
                    letterMap.put(ch, 0);
                }
            }
        }
    }

    private static char[][] makeExpression(String text) {
        String[] allParts = text.split("==");
        String[] leftParts = allParts[0].split("\\+");
        String rightPart = allParts[1].trim();

        int len = leftParts.length + 1;
        char[][] chars = new char[len][];
        chars[len - 1] = rightPart.toCharArray();
        for (int i = 0; i < leftParts.length; i++) {
            chars[i] = leftParts[i].trim().toCharArray();
        }
        return chars;
    }

    public Map<Character, Integer> solve() throws UnsolvablePuzzleException {
        List<Character> keys = getUnsolvedKeys();
        long limit = (long) Math.pow(10, keys.size());
        boolean solvedForOne = keys.size() < letterMap.size();
        boolean[] blockedForZero = getBlockedForZero(keys);

        for (long i = (long) Math.pow(10, keys.size() - 2); i < limit; i++) {
            int[] digits = toDigits(i, keys.size());
            long skip = skip(digits, solvedForOne, blockedForZero);
            if (skip > 0) {
                i += skip - 1;
                continue;
            }

            for (int j = 0; j < digits.length; j++) {
                letterMap.put(keys.get(j), digits[j]);
            }

            if (isSolved()) {
                return letterMap;
            }
        }

        throw new UnsolvablePuzzleException();
    }

    private boolean[] getBlockedForZero(List<Character> keys) {
        boolean[] blocked = new boolean[keys.size()];
        for (int i = 0; i < expression.length; i++) {
            int index = keys.indexOf(expression[i][0]);
            if (index >= 0) {
                blocked[index] = true;
            }
        }
        return blocked;
    }

    private List<Character> getUnsolvedKeys() {
        boolean firstChar = IntStream.range(0, expression.length - 1)
                .map(i -> expression[i].length)
                .anyMatch(len -> len == expression[expression.length - 1].length);

        Set<Character> keys = new HashSet<>(letterMap.keySet());
        if (!firstChar) {
            Character ch = expression[expression.length - 1][0];
            keys.remove(ch);
            letterMap.put(ch, 1);
        }

        return new ArrayList<>(keys);
    }

    private boolean isSolved() {
        int sum = sumRange(0, expression.length - 1);
        int result = sumRow(expression.length - 1);
        return sum == result;
    }

    private int sumRange(int startRow, int endRow) {
        int total = 0;
        for (int row = startRow; row < endRow; row++) {
            total += sumRow(row);
        }
        return total;
    }

    private int sumRow(int row) {
        int total = 0;
        int multi = 1;
        for (int i = expression[row].length - 1; i >= 0; i--) {
            int digit = letterMap.get(expression[row][i]);
            total += digit * multi;
            multi *= 10;
        }
        return total;
    }

    private static int[] toDigits(long value, int size) {
        int[] digits = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            digits[i] = (int) (value % 10);
            value /= 10;
        }
        return digits;
    }

    private static long skip(int[] digits, boolean solvedForOne, boolean[] blockedForZero) {
        boolean[] set = new boolean[10];
        set[1] = solvedForOne;
        for (int i = 0; i < digits.length; i++) {
            int digit = digits[i];
            if (set[digit] || (digit == 0 && blockedForZero[i])) {
                return (long) Math.pow(10, digits.length - 1 - i);
            }
            set[digit] = true;
        }
        return 0;
    }
}   