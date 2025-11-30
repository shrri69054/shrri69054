using System.Collections.Generic;
public static class PrimeFactors
{
    public static IEnumerable<long> Factors(long number)
    {
        for (long i = 2; i <= number; i++)
        {
            if (number % i == 0)
            {
                number /= i;
                yield return i;
                i--;
            }
        }
    }
}