#include "roman_numerals.h"
#include <vector>

namespace roman_numerals {

const std::vector<std::pair<int, std::string>> SYMBOLS{
    {1000, "M"}, {900, "CM"}, {500, "D"}, {400, "CD"}, {100, "C"}, {90, "XC"}, {50, "L"},
    {40, "XL"},  {10, "X"},   {9, "IX"},  {5, "V"},    {4, "IV"},  {1, "I"},
};

std::string convert(int arabic)
{
    std::string roman;
    roman.reserve(20);
    for (const auto &[val, symbol] : SYMBOLS) {
        while (arabic >= val) {
            roman += symbol;
            arabic -= val;
        }
    }
    return roman;
}

} // namespace roman_numerals





