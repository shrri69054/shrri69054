using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;

public class LedgerEntry
{
    public LedgerEntry(DateTime date, string description, decimal change)
    {
        Date = date;
        Description = description;
        Change = change;
    }

    public DateTime Date { get; }
    public string Description { get; }
    public decimal Change { get; }
}

public static class Ledger
{
    public static LedgerEntry CreateEntry(string date, string desc, int chng) =>
        new(DateTime.Parse(date, CultureInfo.InvariantCulture), desc, chng / 100.0m);

    public static string Format(string currency, string locale, LedgerEntry[] entries)
    {
        var culture = CreateCulture(currency, locale);
        var header = GetHeader(locale);
        var sortedEntries = SortEntries(entries);

        var lines = new List<string> { header };
        lines.AddRange(sortedEntries.Select(entry => FormatEntry(entry, culture)));

        return string.Join("\n", lines);
    }

    private static CultureInfo CreateCulture(string currency, string locale)
    {
        if (!IsSupportedCurrency(currency)) throw new ArgumentException("Invalid currency");
        if (!IsSupportedLocale(locale)) throw new ArgumentException("Invalid locale");

        var culture = new CultureInfo(locale);
        culture.DateTimeFormat.ShortDatePattern = locale == "nl-NL" ? "dd/MM/yyyy" : "MM/dd/yyyy";
        culture.NumberFormat.CurrencySymbol = currency == "USD" ? "$" : "€";
        culture.NumberFormat.CurrencyNegativePattern = locale == "nl-NL" ? 12 : 0;

        return culture;
    }

    private static string GetHeader(string locale) => locale switch
    {
        "en-US" => "Date       | Description               | Change       ",
        "nl-NL" => "Datum      | Omschrijving              | Verandering  ",
        _ => throw new ArgumentException("Invalid locale")
    };

    private static string FormatEntry(LedgerEntry entry, IFormatProvider culture)
    {
        var date = entry.Date.ToString("d", culture);
        var description = TruncateDescription(entry.Description);
        var change = FormatChange(entry.Change, culture);

        return $"{date} | {description,-25} | {change,13}";
    }

    private static string TruncateDescription(string desc) =>
        desc.Length > 25 ? desc[..22] + "..." : desc;

    private static string FormatChange(decimal amount, IFormatProvider culture)
    {
        var formatted = amount.ToString("C", culture);
        return amount >= 0 ? formatted + " " : formatted.Contains("-") ? formatted + " " : formatted;
    }

    private static IEnumerable<LedgerEntry> SortEntries(IEnumerable<LedgerEntry> entries)
    {
        return entries
            .OrderBy(e => e.Change < 0 ? 0 : 1) // Ensure negatives come first
            .ThenBy(e => e.Date)
            .ThenBy(e => e.Description)
            .ThenBy(e => e.Change);
    }

    private static bool IsSupportedCurrency(string currency) => currency == "USD" || currency == "EUR";
    private static bool IsSupportedLocale(string locale) => locale == "en-US" || locale == "nl-NL";
}