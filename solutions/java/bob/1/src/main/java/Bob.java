public class Bob {
    String hey(String phrase) {
        String trimmedPhrase = phrase.trim();
        if (trimmedPhrase.isEmpty()) return "Fine. Be that way!";
        if (isUpper(trimmedPhrase) && trimmedPhrase.endsWith("?")) return "Calm down, I know what I'm doing!";
        if (isUpper(trimmedPhrase)) return "Whoa, chill out!";
        if (trimmedPhrase.endsWith("?")) return "Sure.";
        return "Whatever.";
    }

    boolean isUpper(String s) {
        return s.chars().anyMatch(Character::isLetter) && s.equals(s.toUpperCase());
    }
}