using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

public static class Tournament
{
    private const string ResultWin = "win";
    private const string ResultLoss = "loss";
    private const string ResultDraw = "draw";

    private static string Format(string team, string matchesPlayed, string wins, string draws, string losses, string points) =>
        $"{team,-30} | {matchesPlayed,2} | {wins,2} | {draws,2} | {losses,2} | {points,2}";

    private static string Format(string team, int matchesPlayed, int wins, int draws, int losses, int points) =>
        Format(team, matchesPlayed.ToString(), wins.ToString(), draws.ToString(), losses.ToString(), points.ToString());

    private static List<TeamStatistics> ParseMatchResults(Stream inStream)
    {
        var results = new Dictionary<string, TeamStatistics>();

        using (var reader = new StreamReader(inStream))
        {
            while (!reader.EndOfStream)
            {
                var values = reader.ReadLine().Split(";");
                var homeTeam = values[0];
                var awayTeam = values[1];
                var homeTeamResult = values[2];

                if (!results.ContainsKey(homeTeam))
                {
                    results.Add(homeTeam, new TeamStatistics(homeTeam));
                }

                if (!results.ContainsKey(awayTeam))
                {
                    results.Add(awayTeam, new TeamStatistics(awayTeam));
                }

                switch (homeTeamResult)
                {
                    case ResultWin:
                        results[homeTeam].Wins++;
                        results[awayTeam].Losses++;
                        break;
                    case ResultLoss:
                        results[homeTeam].Losses++;
                        results[awayTeam].Wins++;
                        break;
                    case ResultDraw:
                        results[homeTeam].Draws++;
                        results[awayTeam].Draws++;
                        break;
                    default:
                        throw new ArgumentOutOfRangeException();
                }
            }
        }

        return results.Values.ToList();
    }

    public static void Tally(Stream inStream, Stream outStream)
    {
        var results = ParseMatchResults(inStream);

        var teams = results
            .OrderByDescending(team => team.Points)
            .ThenBy(team => team.TeamName);

        using (var writer = new StreamWriter(outStream))
        {
            writer.Write(Format("Team", "MP", "W", "D", "L", "P"));
            foreach (var stats in teams)
            {
                var text = Format(stats.TeamName, stats.MatchesPlayed, stats.Wins, stats.Draws, stats.Losses, stats.Points);
                writer.Write($"\n{text}");
            }
        }
    }

    private class TeamStatistics
    {
        public TeamStatistics(string name)
        {
            TeamName = name;
        }

        private const int PointsForWin = 3;
        private const int PointsForDraw = 1;
        private const int PointsForLoss = 0;

        public string TeamName { get; }
        public int Wins { get; set; } = 0;
        public int Losses { get; set; } = 0;
        public int Draws { get; set; } = 0;

        public int Points => PointsForWin * Wins + PointsForDraw * Draws + PointsForLoss * Losses;
        public int MatchesPlayed => Wins + Draws + Losses;
    }
}