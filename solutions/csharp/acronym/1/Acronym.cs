using System;

public static class Acronym
{
    public static string Abbreviate(string phrase)
    {
        string[] sentance = phrase.Split(new char[] {' ', '_','-' },StringSplitOptions.RemoveEmptyEntries);
        string abberviation = "";
        foreach(string word in sentance)
        {
            abberviation += word[0].ToString().ToUpper();
        }
        return abberviation;
    }
}