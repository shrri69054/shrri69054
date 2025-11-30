using System.Collections.Concurrent;

public static class ParallelLetterFrequency
{
    public static Task<Dictionary<char, int>> Calculate(IEnumerable<string> texts)
    {
        ConcurrentDictionary<char, int> count = new ConcurrentDictionary<char, int>();
        Parallel.ForEach(texts.AsParallel().SelectMany(x => x), letter =>
        {
            if (char.IsLetter(letter))
            {
                count.AddOrUpdate(char.ToLower(letter), 1, (_, val) => val + 1);
            }
        });
        return Task.FromResult(count.ToDictionary());
    }
}