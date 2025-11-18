import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.time.YearMonth;

public class SwiftScheduling {
    public static LocalDateTime convertToDeliveryDate(LocalDateTime meetingStart, String description) {
        switch(description){
            case "NOW" :
                return meetingStart.plusHours(2);
            case "ASAP":
                if(meetingStart.getHour() < 13){
                    return meetingStart.withHour(17).withMinute(0).withSecond(0);
                } else {
                    return meetingStart.plusDays(1).withHour(13).withMinute(0).withSecond(0);
                }
            case "EOW" :
                if(meetingStart.getDayOfWeek().getValue() < DayOfWeek.THURSDAY.getValue()){
                    return meetingStart.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).withHour(17).withMinute(0).withSecond(0);
                } else {
                    return meetingStart.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(20).withMinute(0).withSecond(0);
                }
        }
        if(description.charAt(0) == 'Q'){
            int q = Integer.parseInt(description.substring(1));
            if(meetingStart.getMonthValue() <= 3*q){
                return getLastWorkdayofMonth(meetingStart.withMonth(3*q)).atTime(8,0);
            } else {
                return getLastWorkdayofMonth(meetingStart.plusYears(1).withMonth(3*q)).atTime(8,0);
            }
        } else {
            int m = Integer.parseInt(description.substring(0,description.indexOf('M')));
            if(meetingStart.getMonthValue() < m){
                return getFirstWorkdayofMonth(meetingStart.withMonth(m)).atTime(8,0);
            } else {
                return getFirstWorkdayofMonth(meetingStart.plusYears(1).withMonth(m)).atTime(8,0);
            }
        }
    }

    static LocalDate getLastWorkdayofMonth(LocalDateTime date){
        LocalDate lastDay = YearMonth.of(date.getYear(),date.getMonthValue()).atEndOfMonth();
        while (lastDay.getDayOfWeek() == DayOfWeek.SATURDAY || lastDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            lastDay = lastDay.minusDays(1);
        }
        return lastDay;
    }
    static LocalDate getFirstWorkdayofMonth(LocalDateTime date){
        LocalDate firstDay = YearMonth.of(date.getYear(),date.getMonthValue()).atDay(1);
        while (firstDay.getDayOfWeek() == DayOfWeek.SATURDAY || firstDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            firstDay = firstDay.plusDays(1);
        }
        return firstDay;
    }
}