using System;
using System.Collections.Generic;
using System.Linq;

public static class NthPrime
{
    public static int Prime(int nth)
    {
        if (nth == 0)
        {
            throw new ArgumentOutOfRangeException();
        }
        int num = 1;
        int count = 0;
        int i;

        while (count < nth)
        {
            num++;
            for (i = 2; i <= num; i++)
            {
                if (num % i == 0)
                {
                    break;
                }
            }
            if (i == num)
            {
                count = count + 1;
            }
        }
        return num;
    }
}