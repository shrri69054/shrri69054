#include "hexadecimal.h"
#include <cctype>
#include <map>

namespace {

// Helper: validate string
bool correct(const std::string& str) {
    for (char c : str) {
        if (!std::isdigit(c) && (c < 'a' || c > 'f')) {
            return false;
        }
    }
    return true;
}

// Hex character map
const std::map<char, int> converter{
    {'a', 10}, {'b', 11}, {'c', 12},
    {'d', 13}, {'e', 14}, {'f', 15}
};

} // anonymous namespace


namespace hexadecimal {

int convert(std::string str) {
    if (!correct(str) || str.empty()) {
        return 0;
    }

    int decimal = 0;

    for (char c : str) {
        int digit;

        if (std::isdigit(c)) {
            digit = c - '0';
        } else {
            digit = converter.at(c);
        }

        decimal = decimal * 16 + digit; // better than pow
    }

    return decimal;
}

}  // namespace hexadecimal