import java.util.ArrayList;
import java.util.List;

class LargestSeriesProductCalculator {
    String inputNumberText;

    LargestSeriesProductCalculator(String inputNumber) {
        if (!inputNumber.matches("^\\d*$"))
            throw new IllegalArgumentException("String to search may only contain digits.");

        inputNumberText = inputNumber;
    }

    long calculateLargestProductForSeriesLength(int numberOfDigits) {
        if (numberOfDigits > inputNumberText.length())
            throw new IllegalArgumentException("Series length must be less than or equal to the length of the string to search.");

        if (numberOfDigits < 0)
            throw new IllegalArgumentException("Series length must be non-negative.");

        if (inputNumberText.length() == 0)
            return 1;

        List<Integer> inputDigits = new ArrayList<>();

        long maxSeriesProduct = Character.getNumericValue(inputNumberText.charAt(0));
        inputDigits.add((int)maxSeriesProduct);
        for (int i = 1; i < numberOfDigits && i < inputNumberText.length(); i++) {
            int currentDigit = Character.getNumericValue(inputNumberText.charAt(i));
            inputDigits.add(currentDigit);
            maxSeriesProduct *= currentDigit;
        }

        for (int i = numberOfDigits; i < inputNumberText.length(); i++) {
            int currentDigit = Character.getNumericValue(inputNumberText.charAt(i));
            inputDigits.add(currentDigit);

            long currentSeriesProduct =
                    inputDigits.stream()
                            .skip(i - numberOfDigits + 1)
                            .limit(numberOfDigits)
                            .reduce(1L, (current, aggregated) -> current * aggregated, (left, right) -> left * right);
            if (currentSeriesProduct > maxSeriesProduct) {
                maxSeriesProduct = currentSeriesProduct;
            }
        }

        return maxSeriesProduct;
    }
}