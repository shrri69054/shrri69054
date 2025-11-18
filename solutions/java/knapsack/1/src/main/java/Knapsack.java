import java.util.List;
public class Knapsack {
    Knapsack() {}
    int maximumValue(int maxWeight, List<Item> items) {
        int size = items.size();
        if (maxWeight == 0 || size==0)
            return 0;
        int[][] w = new int[size+1][maxWeight+1];
        for (int j=0; j<size; j++) {
            w[0][j] = 0;
        }
        for (int i=1; i<=size; i++) {
            Item curItem = items.get(i-1);
            for (int j=0; j<=maxWeight; j++) {
                if (curItem.weight > j) {
                    w[i][j] = w[i-1][j];
                }
                else
                    w[i][j] = Math.max(w[i-1][j], curItem.value + w[i-1][j-curItem.weight]);
            }
        }
        return w[size][maxWeight];
    }
}
