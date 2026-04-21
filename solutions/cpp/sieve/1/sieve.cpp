#include "sieve.h"
#include <cstddef>  // ✅ add this

namespace sieve {

std::vector<int> primes(int n) {
    if (n < 2)
        return {};

    std::vector<bool> numbers_table(n + 1, true);
    numbers_table[0] = false;
    numbers_table[1] = false;

    for (std::size_t i = 2; i * i <= static_cast<std::size_t>(n); ++i) {
        if (numbers_table[i]) {
            for (std::size_t j = i * i; j <= static_cast<std::size_t>(n); j += i) {
                numbers_table[j] = false;
            }
        }
    }

    std::vector<int> result;
    result.reserve(n / 2);

    for (std::size_t i = 2; i <= static_cast<std::size_t>(n); ++i) {
        if (numbers_table[i]) {
            result.push_back(static_cast<int>(i));
        }
    }

    return result;
}

}  // namespace sieve