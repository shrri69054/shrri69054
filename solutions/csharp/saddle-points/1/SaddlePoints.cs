using System.Linq;
using System.Collections.Generic;

public static class SaddlePoints
{
    public static IEnumerable<(int, int)> Calculate(int[,] m)
    {
        var ri = Enumerable.Range(0, m.GetLength(0));
        var ci = Enumerable.Range(0, m.GetLength(1));

        var r = from i in ri select from j in ci select m[i, j];
        var c = from i in ci select from j in ri select m[j, i];

        return from i in ri from j in ci
        where r.ElementAt(i).All(x => m[i, j] >= x) 
        where c.ElementAt(j).All(x => m[i, j] <= x)
        select (i + 1, j + 1);
    }
}