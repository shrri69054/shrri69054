#include "collatz_conjecture.h"
#include <stdexcept>

namespace collatz_conjecture {
    int steps(int n) {
        if (n < 1) throw std::domain_error("n must be positive");
        if (n == 1) return 0;
        return 1 + steps((n%2 == 0) ? n/2 : 3*n+1);
    }
}  // namespace collatz_conjecture





