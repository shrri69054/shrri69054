#include "grains.h"

namespace grains {
    unsigned long long square(int num) {
        return 1ULL << (num-1);
    }

    unsigned long long total() {
        return __UINT64_MAX__;
    }
}  // namespace grains
