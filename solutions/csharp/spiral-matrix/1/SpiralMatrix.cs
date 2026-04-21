using System;

public class SpiralMatrix
{
    public static int[,] GetMatrix(int size)
    {
        int[,] matrix = new int[size, size];
        int x = 0, y = 0, max = size - 1, min = 0;

        for(int i = 1; i <= size * size; i++) {
            matrix[y, x] = i;
            if (y == max && x != min)
                x--;
            else if (x == max)
                y++;
            else if (y == min)
                x++;
            else if (x == min && y != min + 1)
                y--;
            else {
                max -= 1;
                min += 1;
                x++;
            }
        }

        return matrix;
    }
}