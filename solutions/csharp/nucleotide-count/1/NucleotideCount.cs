using System;
using System.Collections.Generic;

public static class NucleotideCount
{
    public static IDictionary<char, int> Count(string sequence)
    {
        IDictionary<char, int> nucleotideMap = new Dictionary<char, int>
        {
            ['A'] = 0,
            ['C'] = 0,
            ['G'] = 0,
            ['T'] = 0
        };

        foreach (char nucleotide in sequence)
        {
            if (!nucleotideMap.ContainsKey(nucleotide))
            {
                throw new ArgumentException();
            }

            nucleotideMap[nucleotide]++;
        }

        return nucleotideMap;
    }
}