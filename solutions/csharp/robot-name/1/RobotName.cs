using System;
using System.Collections.Generic;

public class Robot
{
    private static readonly Random Rng = new Random();

    // static HashSet
    private static readonly HashSet<string> RobotTracker = new HashSet<string>();

    public string Name { get; private set; }

    public Robot()
    {
        Reset();
    }

    public void Reset()
    {
        Name = GetUniqueName();
    }

    private string GetUniqueName()
    {
        var robotName = RobotName();
        
        while (!RobotTracker.Add(robotName))
        {
            robotName = RobotName();
        }

        return robotName;
    }

    private string RobotName()
    {
        var answer = new char[5];

        for (var i = 0; i < 2; i++)
        {
            // +1 needed because upper bound on rng.Next is exclusive
            answer[i] = (char)Rng.Next('A', 'Z' + 1);
        }

        for (var i = 2; i < 5; i++)
        {
            answer[i] = (char)Rng.Next('0', '9' + 1);
        }

        return new string(answer);
    }
}