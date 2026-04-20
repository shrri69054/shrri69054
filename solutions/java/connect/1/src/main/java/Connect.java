import java.util.Arrays;
import java.util.List;

class Connect {

    private final List<String> board;

    public Connect(String[] board) {
        this.board = Arrays.asList(board);
    }

    public Winner computeWinner() {
        for (int i = 0; i < board.get(0).length(); i++) {
            if (board.get(0).charAt(i) == 'O')
                if (findPath(0, i, 'O', new LastPosition(-1, -1))) return Winner.PLAYER_O;
        }
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).charAt(i) == 'X')
                if (findPath(i, i, 'X', new LastPosition(-1, -1))) return Winner.PLAYER_X;
        }
        return Winner.NONE;
    }

    private boolean findPath(int y, int x, char player, LastPosition lastPoint) {
        if (player == 'O') {
            if (y == board.size() - 1) return true;
        } else if (x - y == board.get(0).length() - 1) return true;

        //up left
        if (y != 0 && x != 0 && lastPoint.getY() != y - 1 && lastPoint.getX() != x - 2 && board.get(y - 1).charAt(x - 1) == player)
            if (findPath(y - 1, x - 1, player, new LastPosition(y, x))) return true;
        //up right
        if (y != 0 && x != board.get(y).length() - 1 && lastPoint.getY() != y - 1 && lastPoint.getX() != x + 2 && board.get(y - 1).charAt(x + 1) == player)
            if (findPath(y - 1, x + 1, player, new LastPosition(y, x))) return true;
        //down left
        if (y != board.size() - 1 && x != 0 && lastPoint.getY() != y + 1 && lastPoint.getX() != x - 2 && board.get(y + 1).charAt(x - 1) == player)
            if (findPath(y + 1, x - 1, player, new LastPosition(y, x))) return true;
        //down right
        if (y != board.size() - 1 && x != board.get(y).length() - 1 && lastPoint.getY() != y + 1 && lastPoint.getX() != x + 2 && board.get(y + 1).charAt(x + 1) == player)
            if (findPath(y + 1, x + 1, player, new LastPosition(y, x))) return true;
        //left
        if (x - y != 0 && lastPoint.getX() != x - 2 && board.get(y).charAt(x - 2) == player)
            if (findPath(y, x - 2, player, new LastPosition(y, x))) return true;
        //right
        if (x - y != board.get(y).length() - 1 && lastPoint.getX() != x + 2 && board.get(y).charAt(x + 2) == player)
            if (findPath(y, x + 2, player, new LastPosition(y, x))) return true;

        return false;
    }
}

   class LastPosition {

    private final int y;
    private final int x;

    LastPosition(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
