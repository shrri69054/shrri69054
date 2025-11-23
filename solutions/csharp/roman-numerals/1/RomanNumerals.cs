using System;
using System.Collections.Generic;

public static class RomanNumeralExtension
{
    private static readonly IDictionary<int, string> Roman = new Dictionary<int, string>()
    {
        {  1, "I" },
        {  5, "V" },
        {  10, "X" },
        {  50, "L" },
        {  100, "C" },
        {  500, "D" },
        {  1000, "M" }
    };

    public static string ToRoman(this int value)
    {
        string romanOutput = "";
        int[] values = Array.ConvertAll(value.ToString().ToCharArray(), c => int.Parse(c.ToString()));
        int length = values.Length - 1;
        for (int i = 0; i <= length; i++)
        {
            if (values[i] > 0)
            {
                string romanAdd = "";
                for (int j = 0; j < Math.Abs((values[i] + 1) % 5 - 1); j++)
                    romanAdd += Roman[(int)Math.Pow(10, length - i)];
                if (values[i] > 3)
                    romanAdd += Roman[(int)(((values[i] - 4) / 5 + 1) * 5 * Math.Pow(10, length - i))];
                if (values[i] > 5 && values[i] <= 8)
                {
                    char[] romanRev = romanAdd.ToCharArray();
                    Array.Reverse(romanRev);
                    romanAdd = new string(romanRev);
                }
                romanOutput += romanAdd;
            }
        }
        return romanOutput;
    }
}