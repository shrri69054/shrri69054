#include "isbn_verifier.h"
#include <string>

namespace isbn_verifier {
    bool is_valid(std::string isbn) {
        int sum{0};
        int multi{10};
        for (auto c : isbn) {
            // only ten numbers:
            if (multi < 1) return false;
            // handle single digit numbers:
            else if (c >= '0' && c <= '9') {
                sum += multi * (c - '0');
                multi--;
            }
            // handle special case 10 as check digit:
            else if (multi == 1 &&( c == 'X' || c == 'x')) {
                sum += multi * 10;
                multi--;
            }
            else if (c == '-') continue;
            else return false;
        }
        if (sum%11 == 0 && multi == 0) return true;
        return false;
    }
}  // namespace isbn_verifier



