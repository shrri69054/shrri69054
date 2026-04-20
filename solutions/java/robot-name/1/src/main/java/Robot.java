import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Created by iryna on 31.03.16.
 */
public class Robot {

    private static final int CHAR_PREFIX_LENGTH = 2;
    private static final char MIN_CHAR = 'A';
    private static final char MAX_CHAR = 'Z';
    private static final int NUMBER_PREFIX_LENGTH = 3;
    private static final char MIN_NUMBER = '0';
    private static final char MAX_NUMBER = '9';
    private String name = generateName();

    private static Set<String> usedNames = new HashSet<>();

    private static Predicate<String> isNameUnique = name -> !usedNames.contains(name) && addToUsedNames(name);

    private static Supplier<String> namesSupplier = () -> generateRandomPart(MIN_CHAR, MAX_CHAR, CHAR_PREFIX_LENGTH) +
                generateRandomPart(MIN_NUMBER, MAX_NUMBER, NUMBER_PREFIX_LENGTH);
    /**
     * Returns the name of the robot
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Generate the names of robots that consists of 2 random chars and 3 random integers
     * @return
     */
    private String generateName() {
        return Stream.generate(namesSupplier)
                .filter(isNameUnique)
                .findFirst()
                .get();
    }

    /**
     * Generates random sequence of chars or ints of the specified length
     * @param min minimal possible value of the range
     * @param max maximal possible value of the range
     * @param length specifies the length of the sequence
     * @return
     */
    private static String generateRandomPart(final char min, final char max, final int length) {
        return new Random()
                .ints(min, max)
                .limit(length)
                .mapToObj( i -> String.valueOf((char) i))
                .collect(joining(""));
    }

    /**
     * Adds the robot name to the set of used names and returns the result
     * @param name of the robot
     * @return
     */
    private static boolean addToUsedNames(String name) {
        return usedNames.add(name);
    }

    /**
     * Resets the name of the robot
     */
    public void reset() {
        name = generateName();
    }
}