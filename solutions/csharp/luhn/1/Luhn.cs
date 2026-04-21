using System;
using System.Linq;


public static class Luhn
{
    public static bool IsValid(string number)
    {
        if (!number.All(c => char.IsDigit(c) || char.IsWhiteSpace(c)))
            return false;

        if (number.Trim().Length < 2)
            return false;

        return number
            .Reverse()
            .Where(char.IsDigit)
            .Select(c => (int)char.GetNumericValue(c))
            .Select((n, i) => ((i % 2) == 0) ? n : n * 2)
            .Select(n => n > 9 ? n - 9 : n)
            .Sum() % 10 == 0;
    }
}