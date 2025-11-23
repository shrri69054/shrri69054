using System;

public static class Knapsack
{
    public static int MaximumValue(int maximumWeight, (int weight, int value)[] items)
    {
        var dp = new int[maximumWeight + 1];
        for (int i = 0; i < items.Length; i++)
        for (int j = maximumWeight; j >= items[i].weight; j--)
            dp[j] = Math.Max(dp[j], dp[j - items[i].weight] + items[i].value);
        return dp[maximumWeight];
    }
}