using System;
using System.Collections.Generic;
using System.Linq;

public struct Coord
{
    public Coord(ushort x, ushort y)
    {
        X = x;
        Y = y;
    }

    public ushort X { get; }
    public ushort Y { get; }

    // No need for Math.Pow; simple multiplication is faster
    public double DistanceBetweenCoordsSquared(Coord other)
        => (this.X - other.X) * (this.X - other.X)
         + (this.Y - other.Y) * (this.Y - other.Y);
}

public struct Plot
{
    public Plot(Coord coord1, Coord coord2, Coord coord3, Coord coord4)
    {
        Coord1 = coord1;
        Coord2 = coord2;
        Coord3 = coord3;
        Coord4 = coord4;

        LongestSideSquared = CalculateLongestSideSquared(coord1, coord2, coord3, coord4);
    }

    public Coord Coord1 { get; }
    public Coord Coord2 { get; }
    public Coord Coord3 { get; }
    public Coord Coord4 { get; }
    public double LongestSideSquared { get; }

    private static double CalculateLongestSideSquared(Coord a, Coord b, Coord c, Coord d)
    {
        var sides = new double[4];
        sides[0] = a.DistanceBetweenCoordsSquared(b);
        sides[1] = b.DistanceBetweenCoordsSquared(c);
        sides[2] = c.DistanceBetweenCoordsSquared(d);
        sides[3] = d.DistanceBetweenCoordsSquared(a);
        return sides.Max();
    }
}

public class ClaimsHandler
{
    private readonly List<Plot> _plots = new();

    public void StakeClaim(Plot plot)
    {
        if (!IsClaimStaked(plot))
            _plots.Add(plot);
    }

    public bool IsClaimStaked(Plot plot) => _plots.Contains(plot);

    public bool IsLastClaim(Plot plot) => _plots.Count > 0 && plot.Equals(_plots.Last());

    public Plot GetClaimWithLongestSide()
        => _plots.OrderByDescending(p => p.LongestSideSquared).First();
}
