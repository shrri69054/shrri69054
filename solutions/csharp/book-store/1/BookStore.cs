using System;
using System.Collections.Generic;
using System.Linq;

public static class BookStore
{
    static double[] groupMultiplier = new double[] { 0, 8, 15.2, 21.6, 25.6, 30 };
    public static decimal Total(IEnumerable<int> books)
    {
        List<int> bookList = books.GroupBy(s => s).Select(x => x.Count()).ToList();
        List<double> prices = new List<double>();
        if (bookList.Count == 1) 
            return (decimal)(bookList[0] * 8);

        while (bookList.Count > 1)
        {
            double total = 0, amount = 0;
            bookList.Sort();
            for (int j = 0; j < bookList.Count; j++)
            {
                total += (bookList[j] - amount) * groupMultiplier[bookList.Count - j];

                if (bookList[j] > amount) 
                    amount = bookList[j];
            }
            prices.Add(total);
            bookList[0]--;
            bookList[1]++;
            bookList.Remove(0);
        }
        return prices.Count > 0 ? (decimal)prices.Min() : 0m;
    }
}