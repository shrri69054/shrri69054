using System;
using System.Text.RegularExpressions;

public static class Markdown
{
    public static string Parse(string text)
    {
        // this is probably more confusing to understand than the original....
        text = Regex.Replace(text, @"^###### ([^\n$]+)", "<h6>$1</h6>");
        text = Regex.Replace(text, @"^##### ([^\n$]+)", "<h5>$1</h5>");
        text = Regex.Replace(text, @"^#### ([^\n$]+)", "<h4>$1</h4>");
        text = Regex.Replace(text, @"^### ([^\n$]+)", "<h3>$1</h3>");
        text = Regex.Replace(text, @"^## ([^\n$]+)", "<h2>$1</h2>");
        text = Regex.Replace(text, @"^# ([^\n$]+)", "<h1>$1</h1>");
        text = Regex.Replace(text, @"(^|\n)\* ([^\n$]+)", "<li>$2</li>");
        text = Regex.Replace(text, @"^(^|((?!(<li>)).)+)(<li>.+)", "$1<ul>$4");
        text = Regex.Replace(text, @"</li>(\n|$)", "</li></ul>");
        text = Regex.Replace(text, @"__(((?!(__)).)+)__", "<strong>$1</strong>");
        text = Regex.Replace(text, @"_(((?!(_)).)+)_", "<em>$1</em>");
        text = Regex.Replace(text, @"^(<strong>.+</strong>)$", "<p>$1</p>");
        text = Regex.Replace(text, @"^(<em>.+</em>)$", "<p>$1</p>");
        text = Regex.Replace(text, @"^([^<].*)$", "<p>$1</p>");
        text = Regex.Replace(text, @">([\w\s]+)$", "><p>$1</p>");
        return text;
    }
}