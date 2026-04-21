public class SpiralMatrixBuilder {
    public int[][] buildMatrixOfSize(int n) {
        int[][] result = new int[n][n];
        int x = 1;
        int end = n;
        int start = 0;
        while (start <= end) {
            for (int i = start; i < end; i++) {
                result[start][i] = x++;
            }
            for (int i = start + 1; i < end; i++) {
                result[i][end - 1] = x++;
            }
            for (int i = end - 2; i >= start; i--) {
                result[end - 1][i] = x++;
            }
            for (int i = end - 2; i > start; i--) {
                result[i][start] = x++;
            }
            end--;
            start++;
        }
        return result;
    }
}