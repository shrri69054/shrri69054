#include "acronym.h"

#include <cctype>

namespace acronym
{
    std::string acronym(std::string_view name)
    {
        std::string acronym{};
        bool use_next{true};
        for (auto c : name)
        {
            if (c == ' ' || c == '-')
                use_next = true;
            else if (use_next && std::isalpha(c))
            {
                acronym += std::toupper(c);
                use_next = false;
            }
        }

        return acronym;
    }

} // namespace acronym




