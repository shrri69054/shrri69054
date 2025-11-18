class PascalsTriangleGenerator {

    int[][] generateTriangle(int rows) {
        int[][] triangle = new int[rows][];
        for (int r = 0; r < rows; r++) {
            triangle[r] = new int[r + 1];
            triangle[r][0] = 1;
            triangle[r][r] = 1;
            for (int e = 1; e < r; e++) {
                triangle[r][e] = triangle[r - 1][e - 1] + triangle[r - 1][e];
            }
        }
        return triangle;
    }
}