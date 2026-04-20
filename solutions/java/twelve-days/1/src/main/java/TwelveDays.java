import java.util.stream.Collectors;
import java.util.stream.IntStream;

class TwelveDays {

    private static final String[] ORDINAL = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth", "eleventh", "twelfth"};
    private static final String START = "On the %s day of Christmas my true love gave to me: ";
    private static final String[] PARTS = {"a Partridge in a Pear Tree", "two Turtle Doves", "three French Hens", "four Calling Birds", "five Gold Rings", "six Geese-a-Laying", "seven Swans-a-Swimming", "eight Maids-a-Milking", "nine Ladies Dancing", "ten Lords-a-Leaping", "eleven Pipers Piping", "twelve Drummers Drumming"};

    String verse(int verseNumber) {
        String verse;

        if (1 < verseNumber) {
            verse = IntStream.iterate(verseNumber - 1, e -> e - 1).limit(verseNumber - 1)
                    .mapToObj(i -> PARTS[i] + ", ")
                    .collect(Collectors.joining(""));
            verse = verse + "and " + PARTS[0];
        } else {
            verse = PARTS[0];
        }
        return String.format(START, ORDINAL[verseNumber - 1]) + verse + ".\n";
    }

    String verses(int startVerse, int endVerse) {
        String verses;

        verses = IntStream.rangeClosed(startVerse, endVerse)
                .mapToObj(i -> verse(i) + "\n")
                .collect(Collectors.joining(""));
        return verses.substring(0, verses.length() - 1);
    }

    String sing() {
        return verses(1, 12);
    }
}