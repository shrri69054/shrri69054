public class PigLatinTranslator {
    static final String PATTERN;
    static {
        // ?! is zero width negative lookahead
        String no_yt_or_xr_at_start = "(?!yt|xr)";
        String qu_or_consonant_at_start = "(qu|[^aeiou ])";
        String then_qu_or_consonant_except_y = "(qu|[^aeiouy ])*";
        String consonants
            = no_yt_or_xr_at_start
            + qu_or_consonant_at_start
            + then_qu_or_consonant_except_y;
        PATTERN = String.format("(?<consonants>%s)?(?<rest>\\w+)", consonants);
    }

    public static String translate(String input) {
        return input.replaceAll(PATTERN, "${rest}${consonants}ay");
    }
}