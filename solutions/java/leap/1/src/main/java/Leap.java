class Leap {

    boolean isLeapYear(int year) {
        // A leap year is divisible by 4, unless it is divisible by 100 and not divisible by 400
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;  // Divisible by 400, it's a leap year
                } else {
                    return false;  // Divisible by 100 but not 400, not a leap year
                }
            } else {
                return true;  // Divisible by 4 but not 100, it's a leap year
            }
        } else {
            return false;  // Not divisible by 4, not a leap year
        }
    }
}
