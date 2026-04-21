#include "luhn.h"
#include <cctype>

namespace luhn {

bool valid(const std::string& number) {
    // Step 1: Remove spaces and validate characters
    std::string digits;
    for (char c : number) {
        if (std::isdigit(c)) {
            digits += c;
        } else if (!std::isspace(c)) {
            return false;
        }
    }

    // Step 2: Validate length
    if (digits.size() <= 1) {
        return false;
    }

    // Step 3: Apply the Luhn algorithm
    int sum = 0;
    bool double_digit = false;

    for (auto it = digits.rbegin(); it != digits.rend(); ++it) {
        int digit = *it - '0';

        if (double_digit) {
            digit *= 2;
            if (digit > 9) {
                digit -= 9;
            }
        }

        sum += digit;
        double_digit = !double_digit;
    }

    // Step 4: Check if divisible by 10
    return sum % 10 == 0;
}

}  // namespace luhn