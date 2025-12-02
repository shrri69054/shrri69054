using System;
using System.Text.RegularExpressions;
using System.Linq;

public static class CryptoSquare
{
    public static string Ciphertext(string plaintext)
    {
        var normalizedText = Regex.Replace(plaintext.ToLowerInvariant(), "\\W", "");

        var sqrt = Math.Sqrt(normalizedText.Length);
        var rows = (int)Math.Ceiling(sqrt);
        var length = (int)Math.Round(sqrt);

        var text = normalizedText.PadRight(rows * length);

        var chars = Enumerable.Range(0, text.Length)
            .Select(x => rows * ( x % length ) + (int)Math.Floor( (double)(x / length) ))
            .Select(x => text[x]);

        var chunks = Enumerable.Range(0, rows)
            .Select(x => x * length)
            .Select(x => string.Concat(chars).Substring(x, length));

        return string.Join(' ', chunks );
    }
}