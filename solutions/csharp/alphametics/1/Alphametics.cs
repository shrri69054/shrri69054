public static class Alphametics
{
    private static bool TrySolve(Dictionary<char, int> solution, string equation)
    {
        foreach (var kvp in solution)
        {
            equation = equation.Replace(kvp.Key, (char)(kvp.Value + '0'));
        }
        var operands = equation.Split([" + ", " == "], StringSplitOptions.None).Select(long.Parse).ToArray();
        return operands[..^1].Sum() == operands[^1];
    }

    private static string GetSolutionKey(IDictionary<char, int> solution) =>
        string.Concat(solution.OrderBy(kvp => kvp.Key).Select(kvp => $"{kvp.Key}:{kvp.Value}"));

    public static IDictionary<char, int> Solve(string equation)
    {
        var letters = equation.Where(char.IsLetter).ToHashSet();
        var noZero = letters.Where(ch => equation.Contains($" {ch}") || equation[0] == ch).ToHashSet();
        Stack<(Dictionary<char, int> solution, char[] remainingChars, int[] remainingDigits)> visiting = new();
        visiting.Push(([], [.. letters], [.. Enumerable.Range(0, 10).Reverse()]));
        HashSet<string> visited = [];
        while (visiting.TryPop(out var current))
        {
            if (!visited.Add(GetSolutionKey(current.solution))) continue;
            if (current.remainingChars is [])
            {
                if (TrySolve(current.solution, equation)) return current.solution;
                continue;
            }

            foreach (var digit in current.remainingDigits)
            {
                var nextChar = current.remainingChars[0];
                if (digit == 0 && noZero.Contains(nextChar)) continue;

                var newSolution = new Dictionary<char, int>([
                    .. current.solution, new KeyValuePair<char, int>(nextChar, digit)
                ]);
                var newRemainingChars = current.remainingChars[1..];
                var newRemainingDigits = current.remainingDigits.Except([digit]).ToArray();
                visiting.Push((newSolution, newRemainingChars, newRemainingDigits));
            }
        }
        throw new ArgumentException("unsolvable");
    }
}