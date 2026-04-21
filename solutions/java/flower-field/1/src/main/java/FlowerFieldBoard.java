import java.util.ArrayList;
import java.util.List;

class FlowerFieldBoard {
    List<StringBuilder> board = null;

    FlowerFieldBoard(List<String> boardRows) {
        board = new ArrayList<StringBuilder>();
        for (String row: boardRows)
            board.add(new StringBuilder(row));
    }

    List<String> withNumbers() {
        int[] dx = {-1, 0, 1, 0, -1, -1, 1, 1};
        int[] dy = {0, -1, 0, 1, -1, 1, -1, 1};
        for (int i = 0; i < board.size(); i++)
            for (int j = 0; j < board.get(i).length(); j++) {
                if (board.get(i).charAt(j) != '*')
                    continue;
                for (int k = 0; k < dx.length; k++) {
                    int x = i + dx[k];
                    int y = j + dy[k];
                    if (x >= 0 && x < board.size() && y >= 0 && y < board.get(x).length()) {
                        char ch = board.get(x).charAt(y);
                        if (ch == '*')
                            continue;
                        if (ch == ' ')
                            ch = '0';
                        board.get(x).setCharAt(y, ++ch);
                    }
                }
            }
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < board.size(); i++)
            result.add(board.get(i).toString());
        return result;
    }

}