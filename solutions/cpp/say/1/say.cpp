#include "say.h"
#include <stdexcept>
#include <vector>

namespace say {

namespace {

const std::vector<std::string> g_ones = {"zero",  "one",  "two", "three",
                                         "four",  "five", "six", "seven",
                                         "eight", "nine"};

const std::vector<std::string> g_teens = {
    "ten",     "eleven",  "twelve",    "thirteen", "fourteen",
    "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

const std::vector<std::string> g_tens = {"zero",   "ten",   "twenty", "thirty",
                                         "forty",  "fifty", "sixty",  "seventy",
                                         "eighty", "ninety"};

using Thousandess = std::vector<std::pair<std::string, std::int64_t>>;

const Thousandess g_thousandness = {
    {"billion", 1000LL * 1000 * 1000},
    {"million", 1000LL * 1000},
    {"thousand", 1000LL},
    {"", 1}
};

std::string parseTwoDigits(int number) {
    if (number < 10)
        return g_ones.at(number);

    if (number < 20)
        return g_teens.at(number - 10);

    int tens = number / 10;
    int ones = number % 10;

    std::string result = g_tens.at(tens);

    if (ones != 0) {
        result += "-" + g_ones.at(ones);
    }

    return result;
}

std::string parseBelowThousand(int number) {
    std::string result;

    if (number >= 100) {
        int hundreds = number / 100;
        int remainder = number % 100;

        result = g_ones.at(hundreds) + " hundred";

        if (remainder != 0) {
            result += " " + parseTwoDigits(remainder);
        }
    } else {
        result = parseTwoDigits(number);
    }

    return result;
}

std::string splitNumberIntoChunks(std::int64_t number) {
    if (number == 0) {
        return "zero";
    }

    std::string result;

    for (const auto &element : g_thousandness) {
        if (number >= element.second) {
            int chunk = number / element.second;
            number %= element.second;

            if (chunk != 0) {
                result += parseBelowThousand(chunk);

                if (!element.first.empty()) {
                    result += " " + element.first;
                }

                if (number != 0) {
                    result += " ";
                }
            }
        }
    }

    return result;
}

} // anonymous namespace

std::string in_english(std::int64_t number) {
    if (number < 0 || number >= 1000000000000LL) {
        throw std::domain_error("Error");
    }

    return splitNumberIntoChunks(number);
}

} // namespace say