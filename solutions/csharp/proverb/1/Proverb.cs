using System;
using System.Collections.Generic;
using System.Linq;

public class Proverb
{
    private static string ForWantOf((string, string) pair) =>
        $"For want of a {pair.Item1} the {pair.Item2} was lost.";

    private static string[] Parade(IEnumerable<string> items) =>
        items.EachPair().Select(ForWantOf).ToArray();

    public static string[] Recite(string[] strings) =>
        strings.Length == 0 ?
        Array.Empty<string>() :
        Parade(strings)
            .Append($"And all for the want of a {strings[0]}.")
            .ToArray();
}

static class Extensions
{
    public static IEnumerable<(T, T)> EachPair<T>(this IEnumerable<T> col)
    {
        T previousItem = default(T);
        bool isFirst = true;
        foreach(var item in col)
        {
            if (isFirst)
                isFirst = false;
            else
                yield return (previousItem, item);
            previousItem = item;
        }
    }
}