class Queen {
    private static final int MAX_BOARD_SIZE = 7;
    public final int row, column, majorDiagonal, minorDiagonal;
    Queen(int row, int column) {
        if (row < 0) throw new IllegalArgumentException("Queen position must have positive row.");
        if (column < 0) throw new IllegalArgumentException("Queen position must have positive column.");
        if (row > MAX_BOARD_SIZE) throw new IllegalArgumentException("Queen position must have row <= 7.");
        if (column > MAX_BOARD_SIZE) throw new IllegalArgumentException("Queen position must have column <= 7.");
        this.row = row;
        this.column = column;
        this.majorDiagonal = row - column;
        this.minorDiagonal = row + column;
    }
}