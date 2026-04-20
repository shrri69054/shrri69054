import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Flattener {
    public static List<Object> flatten(List<Object> input) {
        return input
                .stream()
                .flatMap(e -> e instanceof List ?
                        Flattener.flatten((List<Object>) e).stream() :
                        Stream.of(e))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}