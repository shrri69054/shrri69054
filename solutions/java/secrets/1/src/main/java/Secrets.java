public class Secrets {

    // 1. Shift bits back (right shift, fill 0s from the left)
    public static int shiftBack(int value, int amount) {
        return value >>> amount; // unsigned right shift
    }

    // 2. Set bits: turn on bits where mask has 1s
    public static int setBits(int value, int mask) {
        return value | mask;
    }

    // 3. Flip bits: invert bits where mask has 1s
    public static int flipBits(int value, int mask) {
        return value ^ mask;
    }

    // 4. Clear bits: turn off bits where mask has 1s
    public static int clearBits(int value, int mask) {
        return value & ~mask;
    }
}
