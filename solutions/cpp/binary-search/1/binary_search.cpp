#include "binary_search.h"
#include <stdexcept>

namespace binary_search
{
    std::size_t find(const std::vector<int> &list, int key, int lo, int hi)
    {
        if (lo > hi)
            throw std::domain_error("Key not in list.");

        int mid = lo + (hi - lo) / 2;
        if (list.at(mid) > key)
            return find(list, key, lo, mid - 1);
        if (list.at(mid) < key)
            return find(list, key, mid + 1, hi);
        return mid;
    }

    std::size_t find(const std::vector<int> &list, int key)
    {
        // This check is needed as
        // list.size() - 1 is calculated as an unsigned expression
        // because list.size() is unsigned int
        // If the list is empty the result will wrap around and create a very large
        // positive number
        if (list.empty())
            throw std::domain_error("list is empty.");

        // To avoid the unsigned issue, we can explicitly cast
        // static_cast<int>(list.size()) - 1
        return find(list, key, 0, list.size() - 1);
    }
} // namespace binary_search





