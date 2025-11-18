import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;

class SumOfMultiples {

    private final int topNumber;
    private final int[] factors;

    SumOfMultiples(int topNumber, int[] factors) {

        this.topNumber = topNumber;
        this.factors = factors;
    }

    int getSum() {
        return range(0, topNumber)
                .filter(number -> stream(factors).anyMatch(factor -> factor > 0 && number % factor == 0))
                .sum();
    }
}