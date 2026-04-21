#include "series.h"
#include <stdexcept>

namespace series
{
    std::vector<std::string> slice(const std::string &input, int k)
    {
        if (k > static_cast<int>(input.size()))
            throw std::domain_error("slice length too large");
        if (k <= 0)
            throw std::domain_error("slice length can't be zero or negative");

        std::vector<std::string> result;
        for (size_t i = 0; i <= input.size() - k; ++i)
            result.push_back(input.substr(i, k));
        return result;
    }
} // namespace series





