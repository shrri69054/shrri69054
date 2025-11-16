import java.util.List;
import java.util.Arrays;

class ResistorColorDuo {
    final List<String> codes = Arrays.asList("black", "brown", "red", "orange", "yellow", "green", "blue", "violet", "grey", "white");

    int value(String[] colors) {
        return codes.indexOf(colors[0]) * 10 + codes.indexOf(colors[1]);
    }
}