import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

class SalonCalendar {

    public LocalDate standingAppointment(int year, Month month, int occurrence, DayOfWeek dayOfWeek) {
        LocalDate date = LocalDate.of(year, month, 1);
        return date.with(TemporalAdjusters.dayOfWeekInMonth(occurrence, dayOfWeek));
    }

    public LocalDate lastAppointment(int year, Month month, DayOfWeek dayOfWeek) {
        LocalDate date = LocalDate.of(year, month, 1);
        return date.with(TemporalAdjusters.lastInMonth(dayOfWeek));
    }

    public LocalDate nextAvailable(LocalDate from, DayOfWeek dayOfWeek) {
        return from.with(TemporalAdjusters.nextOrSame(dayOfWeek));
    }

    public LocalDate bookkeepingDay(int year, Month month) {
        LocalDate date = LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.lastDayOfMonth());

        return date.with(precedingWorkday());
    }

    public LocalDate payday(int year, Month month) {
    LocalDate date = LocalDate.of(year, month, 15);
    return date.with(precedingWorkday());
}

    public TemporalAdjuster precedingWorkday() {
        return temporal -> {
            DayOfWeek day = DayOfWeek.from(temporal);

            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                return temporal.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
            }

            return temporal;
        };
    }
}