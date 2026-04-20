using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;

public static class FoodChain
{
    private static readonly string _verseBreak = "\n\n";
    
    private static readonly Dictionary<int, string> _animals = new() 
    {
        {1, "fly"},
        {2, "spider"},
        {3, "bird"},
        {4, "cat"},
        {5, "dog"},
        {6, "goat"},
        {7, "cow"},
        {8, "horse"}
    };
	
    private static readonly Dictionary<int, string> _secondLine = new() 
    {
        {1, string.Empty},
        {2, "It wriggled and jiggled and tickled inside her.\n"},
        {3, "How absurd to swallow a bird!\n"},
        {4, "Imagine that, to swallow a cat!\n"},
        {5, "What a hog, to swallow a dog!\n"},
        {6, "Just opened her throat and swallowed a goat!\n"},
        {7, "I don't know how she swallowed a cow!\n"},
        {8, "She's dead, of course!"}
    };
	
    public static string Recite(int verseNumber) =>
        FirstLine(verseNumber)
		+ SecondLine(verseNumber)
		+ MiddleLines(verseNumber)
		+ LastLine(verseNumber);

    public static string Recite(int startVerse, int endVerse) =>
		String.Join(_verseBreak,
		    Enumerable.Range(startVerse, endVerse)
			.Select(currentVerse => Recite(currentVerse))
		);

    private static string FirstLine(int verseNumber) =>
        $"I know an old lady who swallowed a {_animals[verseNumber]}.\n";

    private static string SecondLine(int verseNumber) => _secondLine[verseNumber];

    private static string MiddleLines(int verseNumber) =>
		(verseNumber == 8) ? string.Empty : 
		String.Concat(
		Enumerable.Range(2, verseNumber - 1)
			.Reverse()
			.Select(currentVerse => MiddleLine(currentVerse))
		);

    private static string MiddleLine(int verseNumber) =>
		$"She swallowed the {_animals[verseNumber]} to catch the {_animals[verseNumber - 1]}{SpiderBonus(verseNumber)}.\n";
	
	private static string SpiderBonus(int verseNumber) => 
		(verseNumber != 3) ? string.Empty : 
		" that wriggled and jiggled and tickled inside her";

    private static string LastLine(int verseNumber) =>
		(verseNumber == 8) ? string.Empty : 
        $"I don't know why she swallowed the fly. Perhaps she'll die.";
}