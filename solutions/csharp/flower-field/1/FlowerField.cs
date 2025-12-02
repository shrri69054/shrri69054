public static class FlowerField
{
    public static string[] Annotate(string[] input) =>
        Minesweeper.Annotate(input);
}

public static class Minesweeper
{
    private const int HasMine = -1;
    private const int NoMines = 0;
    
    private static string[] FormatGrid(int[][] grid) =>
        grid.Select(row => string.Concat(
            row.Select(mineCount => mineCount switch
                       {
                           NoMines => ' ',
                           HasMine => '*',
                           _ => (char)(mineCount + '0')
                       })))
            .ToArray();

    private static (int r, int c)[] GetForwardNeighbors(int row, int col) =>
        new (int r, int c)[]{
            (row, col + 1),
            (row + 1, col - 1),
            (row + 1, col),
            (row + 1, col + 1),
        };

    public static string[] Annotate(string[] grid)
    {
        var mines = grid.Select(row => row.ToCharArray().Select(_ => NoMines).ToArray()).ToArray();
        List<string> annotated = [];
        for (int row = 0; row < grid.Length; row++)
        {
            var annotatedRow = new List<char>();
            for (int col = 0; col < grid[row].Length; col++)
            {
                var forwardNeighbors = GetForwardNeighbors(row, col);
                if (IsMine(row, col))
                {
                    annotatedRow.Add('*');

                    foreach ((var r, var c) in forwardNeighbors)
                    {
                        MarkMine(r, c);
                    }
                }
                else
                {
                    foreach ((var r, var c) in forwardNeighbors)
                    {
                        if (IsMine(r, c)) MarkMine(row, col);
                    }

                    annotatedRow.Add(mines[row][col] switch
                                     {
                                        0 => ' ',
                                        int count => (char)(count + '0')
                                     });
                }
            }
            annotated.Add(string.Concat(annotatedRow));
        }
        return annotated.ToArray();

        bool IsMine(int r, int c)
        {
            try { return grid[r][c] == '*'; }
            catch { return false; }
        }

        void MarkMine(int r, int c)
        {
            try { mines[r][c]++; }
            catch { /*nothing to mark*/ }
        }
    }
}