using System.Collections.Generic;
using System.Linq;

public enum ConnectWinner { White, Black, None }

internal enum Cell { X, O, E}
internal record BoardInfo ( Cell[][] Board, int Rows, int Cols );

public static class ListExt {
    public static IEnumerable<IEnumerable<T>> Transpose<T>(this IEnumerable<IEnumerable<T>> list) =>
   (!list.Any()) ? list : Enumerable.Range(0, list.First().Count()).Select(x => list.Select(y => y.ElementAt(x)));
}

public class Connect {
    private readonly BoardInfo _info;
    private HashSet<(int r, int c)> _visited;

    public Connect(string[] input) {
        var board = FormatBoard(input);
        _info = new (board, board.Length, board[0].Length);
        _visited = new();
    }    

    private static Cell MapCell(char input) => input switch {'X' => Cell.X,'O' => Cell.O, _ => Cell.E};
    private static Cell[][] FormatBoard(string[] data) => data.Select(r => r.Where(c =>c!=' ').Select(MapCell).ToArray()).ToArray();

    private static bool CanMove(BoardInfo b, (int r, int c) c, Cell player) =>
         c.c >= 0 && c.c < b.Cols && c.r >= 0 && c.r < b.Rows && b.Board[c.r][c.c] == player;

    private static readonly List<(int r, int c)> adjacent = new(){ ( 0, -1 ), (0, 1), (1, 0), (-1, 0), (1, -1), (-1, 1) };       
    private IEnumerable<(int r, int c)> ValidMoves(BoardInfo b, Cell player, (int r, int c) cell) {
        _visited.Add(cell);
        return adjacent
            .Select(p => (r : cell.r + p.r, c: cell.c + p.c))
            .Where(c => CanMove(b, c, player))
            .Where(m => !_visited.Contains(m));
    }

    private bool FindPath(BoardInfo bInfo, Cell player) {
        _visited.Clear();
        bool FindPathImp((int r, int c) cell) =>
           (cell, ValidMoves(bInfo, player, cell)) switch {
               ((var found, _), _) when found == bInfo.Rows - 1 => true,
               (_, var moves)    when !moves.Any()              => false,
               (_, var moves)                                   => moves.Any(FindPathImp)
           };
        return Enumerable.Range(0, bInfo.Cols)
           .Select(col =>(r:0, c:col))
           .Where(cell => bInfo.Board[cell.r][cell.c] == player)
           .Any(FindPathImp);
    }

    private BoardInfo Transpose() => new(_info.Board.Transpose().Select(r => r.ToArray()).ToArray(), _info.Cols, _info.Rows);
    public ConnectWinner Result() =>
        FindPath(_info, Cell.O) ? ConnectWinner.White : FindPath(Transpose(), Cell.X) ? ConnectWinner.Black : ConnectWinner.None; 
}