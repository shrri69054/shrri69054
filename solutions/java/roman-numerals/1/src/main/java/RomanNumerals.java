public class RomanNumerals {
    private static final String[] ROMAN_NUMBERS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private static final int[] ARABIC_NUMBERS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private int number;
    public RomanNumerals(int number) {this.number = number;}

    public String getRomanNumeral() {
        StringBuilder roman = new StringBuilder();
        int remainedValue = number;
        for(int i = 0; i < ARABIC_NUMBERS.length; i++) {
            while(remainedValue >= ARABIC_NUMBERS[i]) {
                remainedValue -= ARABIC_NUMBERS[i];
                roman.append(ROMAN_NUMBERS[i]);
            }
        }
        return roman.toString();
    }
}