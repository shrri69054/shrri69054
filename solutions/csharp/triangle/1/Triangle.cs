using System;

static class Triangle
{
    public static bool IsEquilateral(double a, double b, double c) => IsTriangle(a,b,c) && a == b && b == c;
    public static bool IsIsosceles(double a, double b, double c) => IsTriangle(a,b,c) && (a == b || b == c || a == c);
    public static bool IsScalene(double a, double b, double c) => IsTriangle(a,b,c) && (a != b && b != c && a != c);
    static bool IsTriangle(double a, double b, double c) => a + b > c && a + c > b && b + c > a;
}