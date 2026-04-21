using System;
using System.Linq;

public class SimpleCipher
{
    private const int Letters = 26;
    private readonly Random random = new Random();
    public SimpleCipher()
        => Key = Enumerable.Range(0, 100).Select(x => (char)random.Next('a', 'z' + 1)).Aggregate("", (a, b) => a + b);

    public SimpleCipher(string key) => Key = key;

    public string Key { get; }

    public string Encode(string plaintext)
        => EncodeDecode(plaintext, EncodeSymbol);

    public string Decode(string ciphertext)
        => EncodeDecode(ciphertext, DecodeSymbol);

    private char EncodeSymbol(char letter, char code)
        => (char)((letter + code - 'a' * 2) % Letters + 'a');

    private char DecodeSymbol(char letter, char code)
        => (char)((letter - code + Letters) % Letters + 'a');

    private string EncodeDecode(string str, Func<char, char, char> codeFun)
        => str.Select((c,i) => codeFun(c, Key[i % Key.Length]))
        .Aggregate("", (a, b) => a + b);
}