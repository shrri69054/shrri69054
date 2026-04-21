
#if !defined(ACRONYM_H)
#define ACRONYM_H

#include <string>
#include <string_view>

namespace acronym
{
    std::string acronym(std::string_view name);
} // namespace acronym

#endif // ACRONYM_H