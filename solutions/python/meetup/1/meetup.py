"""
Exercism solution for "meetup"
"""

from datetime import date, timedelta
from enum import Enum


class MeetupDayException(ValueError):
    """
    Error to raise if no meetup can be resolved.
    """


class Weeks(Enum):
    """
    Possible weeks for a meetup, as days to offset by.
    """

    FIRST = 0
    SECOND = 7
    THIRD = 14
    FOURTH = 21
    FIFTH = 28
    LAST = -7
    TEENTH = 12


class Days(Enum):
    """
    Possible days of the week.
    """

    MONDAY = 0
    TUESDAY = 1
    WEDNESDAY = 2
    THURSDAY = 3
    FRIDAY = 4
    SATURDAY = 5
    SUNDAY = 6


def meetup(year: int, month: int, week: str, day: str) -> date:
    """
    Schedule a meetup.
    """
    w, d = Weeks[week.upper()], Days[day.upper()]
    m = month if w is Weeks.LAST else month - 1
    result = date(year + m // 12, m % 12 + 1, 1) + timedelta(days=w.value)
    result += timedelta(days=(d.value - result.weekday()) % 7)
    if result.month != month:
        raise MeetupDayException("That day does not exist.")
    return result