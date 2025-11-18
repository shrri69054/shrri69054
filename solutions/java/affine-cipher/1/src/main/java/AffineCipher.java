import java.math.BigInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class AffineCipher {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final BigInteger ALPHABET_SIZE = BigInteger.valueOf(26);

    private int coefficient1, coefficient2;
    private void setCoefficient1(int coefficient1) {
        assertCoefficient1AndAlphabetSizeAreCoPrime(coefficient1);
        this.coefficient1 = coefficient1;
    }

    private static void assertCoefficient1AndAlphabetSizeAreCoPrime(int coefficient1) {
        if(BigInteger.valueOf(coefficient1).gcd(ALPHABET_SIZE).intValue() > 1)
            throw new IllegalArgumentException("Error: keyA and alphabet size must be coprime.");
    }


    public String encode(String text, int coefficient1, int coefficient2){
        setCoefficient1(coefficient1);
        this.coefficient2 = coefficient2;

        String encoded = text.toLowerCase().chars()
                .filter(Character::isLetterOrDigit)
                .map(this::encode)
                .mapToObj(Character::toString)
                .collect(joining());
        return addSpaces(encoded);

    }

    private int encode(int ascii) {
        if(Character.isDigit(ascii)) {
            return ascii;
        }

        int index = ALPHABET.indexOf(ascii);
        int newIndex = coefficient1 * index + coefficient2;
        newIndex = Math.floorMod(newIndex, ALPHABET.length());
        return ALPHABET.charAt(newIndex);
    }

    private String addSpaces(String encoded) {
        StringBuilder stringBuilder = new StringBuilder(encoded);
        int index = 5;
        while(index < stringBuilder.length()) {
            stringBuilder.insert(index, ' ');
            index += 6;
        }
        return stringBuilder.toString();
    }

    public String decode(String text, int coefficient1, int coefficient2){
        setCoefficient1(coefficient1);
        this.coefficient2 = coefficient2;

        return text.chars()
                .filter(Character::isLetterOrDigit)
                .map(this::decode)
                .mapToObj(Character::toString)
                .collect(joining());
    }

    private int decode(int ascii) {
        if(Character.isDigit(ascii)) {
            return ascii;
        }

        int index = ALPHABET.indexOf(ascii);
        int newIndex =  getModularMultiplicativeInverse() * (index - coefficient2);
        newIndex = Math.floorMod(newIndex, ALPHABET.length());
        return ALPHABET.charAt(newIndex);
    }

    private int getModularMultiplicativeInverse() {
        return IntStream.iterate(1, i -> i + 1)
                .filter(mmi -> (coefficient1 * mmi) % ALPHABET.length() == 1)
                .findFirst()
                .orElseThrow();
    }

}