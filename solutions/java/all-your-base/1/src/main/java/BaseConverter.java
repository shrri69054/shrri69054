import java.util.*;

public class BaseConverter {
    private int value = 0;

    public BaseConverter(int base, int[] number) {
        if (base < 2) throw new IllegalArgumentException("Bases must be at least 2.");

        int l = number.length;
        for (int i = 0; i < l; i++) {
            int v = number[l-i-1];
            if (v >= base) throw new IllegalArgumentException("All digits must be strictly less than the base.");
            if (v < 0) throw new IllegalArgumentException("Digits may not be negative.");
            value += v * (int) Math.pow(base, i);
        }
    }

    public List<Integer> convertToBase(int base) {
        if (base < 2) throw new IllegalArgumentException("Bases must be at least 2.");
        if (value == 0) return Arrays.asList(0);

        LinkedList<Integer> result = new LinkedList<>();
        while (value != 0) {
            result.addFirst(value % base);
            value /= base;
        }
        return result;
    }
    
}