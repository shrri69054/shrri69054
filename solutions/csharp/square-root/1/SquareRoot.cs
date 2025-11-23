using System;

public static class SquareRoot
{
    public static int Root(int number)
    {
        int x = 0;
        while (x * x != number)
        {
            x++;
        }
        return x;
    }
}