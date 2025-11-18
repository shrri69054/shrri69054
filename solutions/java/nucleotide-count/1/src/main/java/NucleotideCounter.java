import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.IntStream;

public class NucleotideCounter {

    private HashMap<Character, Integer> counts = new HashMap<>() {{
        Stream.of('A', 'G', 'C', 'T').forEach(c -> put(c, 0));
    }};

    private final Consumer<Character> isValid = c -> {
        if (!counts.containsKey(c)) throw new IllegalArgumentException();
    };

    public NucleotideCounter(String strand) {
        IntStream.range(0, strand.length())
                 .mapToObj(strand::charAt)
                 .peek(isValid)
                 .forEach(c -> counts.put(c, counts.get(c) + 1));
    }

    public Map<Character, Integer> nucleotideCounts() {
        return counts;
    }

}