using System;

public static class ResistorColorTrio
{
    private static ulong Value(string color) => color.ToLower() switch
        {
            "black" => 0,
            "brown" => 1,
            "red" => 2,
            "orange" => 3,
            "yellow" => 4,
            "green" => 5,
            "blue" => 6,
            "violet" => 7,
            "grey" => 8,
            "white" => 9,
            _ => throw new ArgumentException("unknown color")
        };

    private static ulong Value(string[] colors)
    {
        var values = colors.Select(Value).ToArray();
        var significantValue = values[0] * 10UL + values[1];
        return significantValue * (ulong)Math.Pow(10, values[2]);
    }

    private static (ulong value, string prefix) MetricReduce(ulong initialValue) =>
        initialValue switch
        {
                >= 1000000000UL => (initialValue / 1000000000UL, "giga"),
                >= 1000000 => (initialValue / 1000000, "mega"),
                >= 1000 => (initialValue / 1000, "kilo"),
                _ => (initialValue, string.Empty)
        };
    
    public static string Label(string[] colors)
    {
        var (reducedValue, prefix) = MetricReduce(Value(colors));
        return $"{reducedValue} {prefix}ohms";
    }
    
}