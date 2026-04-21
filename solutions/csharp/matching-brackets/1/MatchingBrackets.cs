using System;
using System.Collections.Generic;

public static class MatchingBrackets
{
    private static readonly Dictionary<char, char> bracketDict = new Dictionary<char, char>() { { '{', '}' }, { '[', ']' }, { '(', ')' }, };
    public static bool IsPaired(string input)
    {
        var bracketStack = new Stack<char>();

        foreach(var character in input)
        {
            if(bracketDict.ContainsKey(character))
                bracketStack.Push(bracketDict[character]);
            else if(bracketDict.ContainsValue(character) && (!bracketStack.TryPop(out char popped) || popped != character))
                return false;
        }

        return bracketStack.Count == 0;
    }
}