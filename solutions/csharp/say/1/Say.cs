using System;
using System.Collections.Generic;

public static class Say
{
    static Dictionary<long, string> digitNames = new Dictionary<long, string>();
    static Dictionary<long, string> upToTwentyNames = new Dictionary<long, string>();
    static Dictionary<long, string> tensNames = new Dictionary<long, string>();
    static Say()
    {
        digitNames.Add(0, "zero");
        digitNames.Add(1, "one");
        digitNames.Add(2, "two");
        digitNames.Add(3, "three");
        digitNames.Add(4, "four");
        digitNames.Add(5, "five");
        digitNames.Add(6, "six");
        digitNames.Add(7, "seven");
        digitNames.Add(8, "eigth");
        digitNames.Add(9, "nine");
        upToTwentyNames.Add(0, "ten");
        upToTwentyNames.Add(1, "eleven");
        upToTwentyNames.Add(2, "twelve");
        upToTwentyNames.Add(3, "thirteen");
        upToTwentyNames.Add(4, "fourteen");
        upToTwentyNames.Add(5, "fifteen");
        upToTwentyNames.Add(6, "sixteen");
        upToTwentyNames.Add(7, "seventeen");
        upToTwentyNames.Add(8, "eigteen");
        upToTwentyNames.Add(9, "nineteen");
        tensNames.Add(1, "ten");
        tensNames.Add(2, "twenty");
        tensNames.Add(3, "thirty");
        tensNames.Add(4, "forty");
        tensNames.Add(5, "fifty");
        tensNames.Add(6, "sixty");
        tensNames.Add(7, "seventy");
        tensNames.Add(8, "eighty");
        tensNames.Add(9, "ninety");
        tensNames.Add(10, "hundred");
    }
    const long tens = 10;
    const long hundreds = 10 * 10;
    const long thousands = hundreds * tens;
    const long millions = thousands * hundreds * tens;
    const long billions = millions * hundreds * tens;
    public static string InEnglish(long number)
    {
        if (number <0 || number > 999999999999) throw new ArgumentOutOfRangeException();
        if (number < tens)
            return GetGetDigit(number);
        if (number < hundreds)
            return GetTens(number);
        if (number < thousands)
            return GetRecurse(number, hundreds, " hundred");
        if (number < millions)
            return GetRecurse(number, thousands, " thousand");
        if (number < billions)
            return GetRecurse(number, millions, " million");
        if (number <= 999999999999)
            return GetRecurse(number, billions, " billion");
        throw new ArgumentOutOfRangeException();
    }
    public static string GetRecurse(long ths, long divider, string marker)
    {
        long hund = ths / divider;
        long remainder = ths % divider;
        var dec = InEnglish(hund) + marker;
        if (remainder > 0)
            dec += " " + InEnglish(remainder);
        return dec;
    }
    public static string GetTens(long ten)
    {
        long hund = ten / tens;
        long remainder = ten % tens;
        if (hund == 1)
            return upToTwentyNames[remainder];
        var dec = tensNames[hund];
        if (remainder > 0)
            dec += "-" + GetGetDigit(remainder);
        return dec;
    }
    public static string GetGetDigit(long ten)
    {
        return digitNames[ten];
    }
}