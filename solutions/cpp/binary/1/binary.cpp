#include "binary.h"

namespace binary
{
    unsigned int convert(std::string_view binary)
    {
        unsigned int decimal{};
        for (auto digit : binary)
        {
            if (digit < '0' || digit > '1')
                return 0;
            decimal <<= 1;
            if (digit == '1')
                decimal |= 1;
        }

        return decimal;
    }

} // namespace binary





