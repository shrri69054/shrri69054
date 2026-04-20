import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.SortedMap;

public class PalindromeCalculator {
	public SortedMap<Long, List<List<Integer>>> getPalindromeProductsWithFactors(int a, int b) {
		if (a > b)
			throw new IllegalArgumentException("invalid input: min must be <= max");

		SortedMap<Long, List<List<Integer>>> result = new TreeMap<>();

		for (int i = a; i <= b; i++) {
			for (int j = i; j <= b; j++) {
				final long product = i * j;
				if (isPalindrome(String.valueOf(product)))
					result.computeIfAbsent(product, p -> new ArrayList<>()).add(Arrays.asList(i, j));
			}
		}

		return result;
	}

	private static boolean isPalindrome(String s) {
		for (int i = 0, j = s.length() - 1; i <= s.length() / 2; i++, j--) {
			if (s.charAt(i) != s.charAt(j)) {
				return false;
			}
		}

		return true;
	}
}