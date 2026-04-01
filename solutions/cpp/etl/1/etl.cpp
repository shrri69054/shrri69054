#include "etl.h"
#include <cctype>  // for std::tolower

namespace etl {

std::map<char, int> transform(const std::map<int, std::vector<char>>& old) {
    std::map<char, int> result;

    for (const auto& pair : old) {
        for (char c : pair.second) {
            result[std::tolower(c)] = pair.first;
        }
    }

    return result;
}

}