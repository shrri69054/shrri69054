using System;
using System.Linq;
using System.Text.RegularExpressions;

public static class Pangram
{
    public static bool IsPangram(string input)
    {
        int CharacterIndex(char character) => character - 'a';

        string valid_chars = "abcdefghijklmnopqrstuvwxyz";
        bool[] hits = new bool[valid_chars.Length];

        string stripped_input = Regex.Replace(input.ToLower(), "[^a-z]", "");
        foreach (char c in stripped_input)
        {
            hits[CharacterIndex(c)] = true;
        }

        return hits.Aggregate(true, (a, b) => a && b);
    }
}
