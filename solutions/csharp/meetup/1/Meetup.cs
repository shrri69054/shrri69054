using System;
using System.Collections.Generic;

public enum Schedule
{
    Teenth,
    First,
    Second,
    Third,
    Fourth,
    Last
}

public class Meetup
{
    private readonly int _month;
    private readonly int _year;

    public Meetup(int month, int year)
    {
        _month = month;
        _year = year;
    }

    public DateTime Day(DayOfWeek dayOfWeek, Schedule schedule)
    {
        var firstDay = new DateTime(_year, _month, schedule == Schedule.Teenth ? 13 :
                                                   schedule == Schedule.First  ? 1 :
                                                   schedule == Schedule.Second ? 8 :
                                                   schedule == Schedule.Third  ? 15 :
                                                   schedule == Schedule.Fourth ? 22 :
                                                                                 DateTime.DaysInMonth(_year, _month) - 6);
        return firstDay.AddDays((dayOfWeek - firstDay.DayOfWeek + 7) % 7);
    }
}