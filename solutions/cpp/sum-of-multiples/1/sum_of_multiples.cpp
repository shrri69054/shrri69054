#include "sum_of_multiples.h"

#include <unordered_set>

namespace sum_of_multiples {
int to(const std::vector<int>& items, int level) {
    int result = 0;
    std::unordered_set<int> unique;
    for (const int item : items) {
        for (int m = item; m < level; m += item) {
            unique.insert(m);
        }
    }
    for (const int m : unique) {
        result += m;
    }
    return result;
}
}  // namespace sum_of_multiples




