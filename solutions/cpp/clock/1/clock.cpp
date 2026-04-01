#include "clock.h"
#include <iomanip>
#include <sstream>

namespace date_independent {

clock::clock(int minutes) {
    const int minutes_per_day = 24 * 60;
    // Normalização para garantir que o tempo esteja sempre entre 0 e 1439
    total_minutes = ((minutes % minutes_per_day) + minutes_per_day) % minutes_per_day;
}

clock clock::at(int hours, int minutes) {
    return clock(hours * 60 + minutes);
}

clock clock::plus(int minutes) const {
    return clock(total_minutes + minutes);
}

clock clock::minus(int minutes) const {
    return clock(total_minutes - minutes);
}

bool clock::operator==(const clock& other) const {
    return total_minutes == other.total_minutes;
}

bool clock::operator!=(const clock& other) const {
    return !(*this == other);
}

clock::operator std::string() const {
    int h = total_minutes / 60;
    int m = total_minutes % 60;
    
    std::ostringstream oss;
    oss << std::setfill('0') << std::setw(2) << h << ":" 
        << std::setfill('0') << std::setw(2) << m;
    return oss.str();
}

} // namespace date_independent




