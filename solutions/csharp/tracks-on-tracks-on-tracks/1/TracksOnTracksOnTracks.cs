using System;
using System.Collections.Generic;
using System.Linq;

public static class Languages
{
    public static List<string> NewList() => new List<string>();

    public static List<string> GetExistingLanguages() => languagesToLearn;

    public static List<string> AddLanguage(List<string> languages, string language) => languages.Append(language).ToList();

    public static int CountLanguages(List<string> languages) => languages.Count;

    public static bool HasLanguage(List<string> languages, string language) => languages.Contains(language);

    public static List<string> ReverseList(List<string> languages) => languages.Reverse<string>().ToList();

    public static bool IsExciting(List<string> languages) 
    {
        if (languages.Count == 0)
            return false;
        else if (languages.First() == excitingLanguage)
            return true;
        else if (languages[1] == excitingLanguage && (languages.Count is >= 2 and <= 3) )  
            return true;
        else 
            return false;
    }

    public static List<string> RemoveLanguage(List<string> languages, string language) => languages.Where(x => x != language).ToList();


    public static bool IsUnique(List<string> languages) => languages.Distinct().Count() == languages.Count;

    #region Fixed values 
    private static List<string> languagesToLearn = new List<string>() {"C#", "Clojure", "Elm"};
    private static string excitingLanguage = "C#";
    #endregion
}