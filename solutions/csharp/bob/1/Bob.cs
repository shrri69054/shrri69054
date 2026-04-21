using System;
using System.Linq;

public static class Bob
{
    public static string Response(string statement)
    {
        if (statement.TrimEnd().EndsWith("?"))
        {
            if (statement.ToUpper().Equals(statement) && statement.Any(c => char.IsLetter(c)))
                { return "Calm down, I know what I'm doing!"; }

            return "Sure.";
        }
        else if (statement.ToUpper().Equals(statement) && statement.Length > 0 && statement.Any(c => char.IsLetter(c))) 
                { return "Whoa, chill out!"; }
        else if (statement.Trim().Length == 0) { return "Fine. Be that way!"; }
        return "Whatever.";
    }
}