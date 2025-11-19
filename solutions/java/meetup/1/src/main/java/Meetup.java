import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

class Meetup {
    private final int month, year;

    Meetup(int monthOfYear, int year) {
        this.month = monthOfYear;
        this.year = year;
    }

    LocalDate day(DayOfWeek dayOfWeek, MeetupSchedule schedule) {
        int monthLength = YearMonth.of(year, month).lengthOfMonth();
        return switch (schedule) {
            case FIRST -> findFirst(dayOfWeek);
            case SECOND -> findFirst(dayOfWeek).plusDays(7);
            case THIRD -> findFirst(dayOfWeek).plusDays(14);
            case FOURTH -> findFirst(dayOfWeek).plusDays(21);
            case LAST -> findDayInRange(dayOfWeek, getLocalDateRange(monthLength-6, monthLength) );
            case TEENTH -> findDayInRange(dayOfWeek, getLocalDateRange(13,19));
        };
    }

    private LocalDate findFirst(DayOfWeek dayOfWeek) {
        return findDayInRange(dayOfWeek, getLocalDateRange(1,7));
    }

    private List<LocalDate> getLocalDateRange(int start, int endInclusive) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = start; i <= endInclusive; i++) {
            dates.add(LocalDate.of(year, month, i));
        }
        return dates;
    }

    private LocalDate findDayInRange(DayOfWeek dayOfWeek, List<LocalDate> dates) {
        for (LocalDate date: dates) {
            if (date.getDayOfWeek().equals(dayOfWeek)) return date;
        }
        return null;
    }

}