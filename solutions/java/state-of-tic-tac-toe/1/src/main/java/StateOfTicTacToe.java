import java.util.List;
import java.util.ArrayList;

class StateOfTicTacToe {
    int full = 9;
    int cntX = 0, cntO = 0;
    List<String> directions = new ArrayList<>();
    
    public GameState determineState(String[] board) {
        check(board);
        
        int cnt = cntX-cntO;
        if (cnt>1) throw new IllegalArgumentException("Wrong turn order: X went twice");
        if (cnt<0) throw new IllegalArgumentException("Wrong turn order: O started");
        
        if (directions.contains("XXX") && directions.contains("OOO")) throw new IllegalArgumentException("Impossible board: game should have ended after the game was won");
        if (directions.contains("XXX") || directions.contains("OOO")) return GameState.WIN;
        
        if (full==9) return GameState.DRAW;
        
        return GameState.ONGOING;
    }

    private void check(String[] board) {
        String col = "", diag1 = "", diag2 = "";
        
        for (int i=0; i<3; i++) {
            directions.add(board[i]);
            diag1 += board[i].charAt(i);
            diag2 += board[i].charAt(2-i);
            
            col = "";
            for (int j=0; j<3; j++) {
                col += board[j].charAt(i);
                if (board[j].charAt(i)=='X') cntX++;
                if (board[j].charAt(i)=='O') cntO++;
                if (board[j].charAt(i)==' ') full--;
            }
            directions.add(col);
        }
        directions.add(diag1);
        directions.add(diag2);
    }
}