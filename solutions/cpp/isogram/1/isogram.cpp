#include "isogram.h"

#include <set>

namespace isogram {

    bool is_isogram(std::string_view word) {
        std::set<char> bag{};
        for(auto c : word) {
            if(c >= 'A' && c <= 'Z') {
                c = c - 'A' + 'a';
            }
            if (c != ' ' && c != '-') {
                if (auto search = bag.find(c); search != bag.end()) {
                    return false;
                }
                bag.insert(c);
            }
        }
        return true;
    }

}  // namespace isogram





