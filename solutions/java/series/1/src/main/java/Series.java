import java.util.ArrayList;
import java.util.List;

public class Series {
    private final String input;

    public Series(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("series cannot be empty");
        }
        this.input = input;
    }

    public List<String> slices(int sliceLength) {
        if (sliceLength <= 0) {
            throw new IllegalArgumentException("slice length cannot be negative or zero");
        }
        if (sliceLength > input.length()) {
            throw new IllegalArgumentException("slice length cannot be greater than series length");
        }

        List<String> result = new ArrayList<>();
        for (int i = 0; i <= input.length() - sliceLength; i++) {
            result.add(input.substring(i, i + sliceLength));
        }
        return result;
    }
}