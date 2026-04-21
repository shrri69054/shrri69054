#include "reverse_string.h"

namespace reverse_string {

std::string reverse_string(std::string s) {
    std::string result{""};
    for (char& ch : s) {
        result = ch + result;
    }
    return result;
}

}  // namespace reverse_string