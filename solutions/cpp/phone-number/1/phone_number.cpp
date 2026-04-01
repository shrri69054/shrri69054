#include "phone_number.h"
#include <string>
#include <stdexcept>
#include <algorithm>

namespace phone_number {
    namespace {
        // normalize by removing all non digit characters
        std::string normalize(const std::string& input) {
            // create local string copy
            std::string digits = input;
            // identify nondigit characters
            auto non_digit = [](char c) {
                return !std::isdigit(static_cast<unsigned char>(c));
            };
            // now remove and return
            digits.erase(
                std::remove_if(digits.begin(), digits.end(), non_digit), digits.end()
            );
            return digits;
        } // end of normalize() function
    } // anonymous namespace
    // define your constructor
    phone_number::phone_number(const std::string& input) {
        digits = normalize(input);
        // check length
        if (digits.length() == 11 && digits[0] == '1') {
            digits = digits.substr(1); // remove the leading 1
        }
        if (digits.length() != 10) { // otherwise there could be 10 numbers given
            throw std::domain_error("Invalid phone number given.");
        }
        // check area code
        if (digits[0] == '1' || digits[0] == '0') {
            throw std::domain_error("Invalid phone number given.");
        }
        // check exchange code
        if (digits[3] == '0' || digits[3] == '1') {
            throw std::domain_error("Invalid phone number given.");
        }
    }
    std::string phone_number::number() const {
        return digits;
    }
}  // namespace phone_number





