#include "trinary.h"
#include <cmath>

namespace trinary {

int to_decimal(std::string tri) {
    int base10 = 0;
    int power = 0;

    for (auto it = tri.rbegin(); it != tri.rend(); ++it, ++power) {
        if (*it < '0' || *it > '2') {
            return 0;  // invalid trinary number
        }
        int digit = *it - '0';
        base10 += digit * std::pow(3, power);
    }

    return base10;
}

}