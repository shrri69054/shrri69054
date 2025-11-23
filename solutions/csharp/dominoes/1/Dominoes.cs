using System.Collections.Generic;
using System.Linq;

public static class Dominoes
{
    public static bool CanChain(IEnumerable<(int, int)> dominoes) => TryChain(dominoes.ToList(), (0, 0));

    public static bool TryChain(List<(int, int)> dominoes, (int first, int last) state)
    {
        if (dominoes.Count == 0 && state.last == state.first)
            return true;                        // all dominoes placed and last matches first

        for (int i = 0; i < dominoes.Count; i++)
        {
            var (a, b) = dominoes[i];
            if (state.last == 0)                // first domino of chain
                state = (a, b);
            else if (state.last == a)           // domino matches state
                state.last = b;
            else if (state.last == b)           // (flipped) domino matches state
                state.last = a;
            else                                // this domino doesn't match
                continue;

            var dominoesCopy = new List<(int, int)>(dominoes);
            dominoesCopy.RemoveAt(i);
            if (TryChain(dominoesCopy, state))  // try to chain next domino to this state
                return true;                    
        }
        return false;
    }
}