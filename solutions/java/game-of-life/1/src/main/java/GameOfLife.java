class GameOfLife {
    public int[][] tick(int[][] matrix){
        int[][] next = new int[matrix.length][];

        for (int row = 0; row < matrix.length; row++) {
            next[row] = new int[matrix[row].length];
            for (int col = 0; col < matrix[row].length; col++) {
                int currentCell = matrix[row][col];

                int liveNeighbors = countLiveNeighbors(row, col, matrix);
                if (
                        // is Live && has two or three live neighbors => start
                        (currentCell == 1 && (liveNeighbors == 2 || liveNeighbors == 3)) ||
                        // is dead && has three live neighbors
                        (currentCell == 0 && liveNeighbors == 3)
                ) {
                    next[row][col] = 1;
                } else {
                    next[row][col] = 0;
                }
            }
        }

        return next;
    }

    // not super efficient, as each field gets checked the amount of neighbors it has
    private int countLiveNeighbors(int row, int col, int[][] matrix) {
        int count = 0;
        // up
        if (row > 0 && matrix[row-1][col] == 1) count++;
        // upper left
        if (row > 0 && col > 0 && matrix[row-1][col-1] == 1) count++;
        // upper right
        if (row > 0 && col < matrix[row].length-1 && matrix[row-1][col+1] == 1) count++;
        // left
        if (col > 0 && matrix[row][col-1] == 1) count++;
        // right
        if (col < matrix[row].length-1 && matrix[row][col+1] == 1) count++;
        // lower left
        if (row < matrix.length-1 && col > 0 && matrix[row+1][col-1] == 1) count++;
        // lower
        if (row < matrix.length-1 && matrix[row+1][col] == 1) count++;
        // lower right
        if (row < matrix.length-1 && col < matrix[row].length-1 && matrix[row+1][col+1] == 1) count++;

        return count;
    }
}