#include "knapsack.h"
#include <cstddef>

namespace knapsack {

int maximum_value(int max_weight, const std::vector<Item> &items)
{
    auto search = [&items](int maxw, size_t i, auto &&search) {
        if (i == items.size())
            return 0;
        int v = 0;
        if (items[i].weight <= maxw) {
            v = std::max(v, items[i].value + search(maxw - items[i].weight, i + 1, search));
        }
        v = std::max(v, search(maxw, i + 1, search));
        return v;
    };
    return search(max_weight, 0, search);
}

}  // namespace knapsack





