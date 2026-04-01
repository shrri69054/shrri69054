#pragma once

#include <string>

namespace date_independent {

class clock {
public:
    static clock at(int hours, int minutes = 0);

    clock plus(int minutes) const;
    clock minus(int minutes) const;

    bool operator==(const clock& other) const;
    bool operator!=(const clock& other) const;
    
    // Sobrecarga para conversão explícita em string
    operator std::string() const;

private:
    int total_minutes;
    explicit clock(int minutes);
};

} // namespace date_independent