public static class PlayAnalyzer
{
    public static string AnalyzeOnField(int shirtNum) =>
        shirtNum switch
        {
            1 => "goalie",
            2 => "left back",
            3 or 4 => "center back",
            5 => "right back",
            6 or 7 or 8 => "midfielder",
            9 => "left wing",
            10 => "striker",
            11 => "right wing",
            _ => "UNKNOWN"
        };

    public static string AnalyzeOffField(object report) =>
        report switch
        {
            int count => $"There are {count} supporters at the match.",
            string message => message,
            Injury incident => $"Oh no! {incident.GetDescription()} Medics are on the field.",
            Incident incident => incident.GetDescription(),
            Manager manager
                => manager.Club is null ? manager.Name : $"{manager.Name} ({manager.Club})",
            _ => "" // Return empty string for unhandled types
        };
}