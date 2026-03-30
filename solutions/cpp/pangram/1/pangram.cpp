#include "pangram.h"

#include <bitset>
#include <cctype>

namespace pangram {

bool is_pangram(const std::string& sentence) {
    std::bitset<26> alphabet{};

    for (unsigned char c : sentence) {
        if (std::isalpha(c)) {
            alphabet.set(std::tolower(c) - 'a');
        }
    }

    return alphabet.all();
}

} // namespace pangram