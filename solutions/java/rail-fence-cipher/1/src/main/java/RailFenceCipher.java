import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
class RailFenceCipher {
	private final int rails;
	private final int amplitude;
	RailFenceCipher(final int rails) {
		this.rails = rails;
		this.amplitude = (rails - 1) * 2;
	}
	String getEncryptedData(final String message) {
		return IntStream.range(0, message.length()).boxed()
			.sorted(Comparator.comparingInt(this::getRail))
			.map(i -> message.substring(i, i + 1))
			.collect(Collectors.joining());
	}
	String getDecryptedData(final String message) {
		return Stream.iterate(this.getEncryptedData(message),
							  s -> !s.equals(message),
							  s -> s = this.getEncryptedData(s))
			.reduce((a, b) -> b)
			.orElse("");
	}
	private int getRail(final int i) {
		final int j = i % this.amplitude;
		return j < rails ? j : this.amplitude - j;
	}
}