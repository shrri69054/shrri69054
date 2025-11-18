import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.lang.Math;
import java.util.List;
public class Sieve{
  
  final int limit;
  public Sieve(int limit){
    this.limit = limit;
  }
  public List<Integer> getPrimes(){
    return IntStream.rangeClosed(2, limit).filter(Sieve::isPrime).boxed().collect(Collectors.toList());
  }
  public static boolean isPrime(int number){
    return IntStream.rangeClosed(2, (int) Math.sqrt(number)).allMatch(n -> number % n != 0);
  }
}