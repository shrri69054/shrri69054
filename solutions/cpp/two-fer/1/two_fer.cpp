#include "two_fer.h"

#include <sstream>

namespace two_fer
{
    std::string two_fer(const std::string &name)
    {
        std::ostringstream ss;
        ss << "One for " << name << ", one for me.";
        return ss.str();
    }

} // namespace two_fer
