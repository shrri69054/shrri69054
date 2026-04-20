using System.Collections.Generic;
using System.Linq;

public static class ScrabbleScore
{
    private static readonly Dictionary<string, int> LetterScores = new Dictionary<string, int>
    {
        ["AEIOULNRST"] = 1,
        ["DG"] = 2,
        ["BCMP"] = 3,
        ["FHVWY"] = 4,
        ["K"] = 5,
        ["JX"] = 8,
        ["QZ"] = 10
    };

    public static int Score(string input)
        => input
        .Select(c => LetterScores.First(kvp => kvp.Key.Contains(char.ToUpper(c))).Value)
        .Sum();
}