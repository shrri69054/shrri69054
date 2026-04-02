#if !defined(BINARY_H)
#define BINARY_H

#include <string_view>

namespace binary {
    unsigned int convert(std::string_view binary);
}  // namespace binary

#endif // BINARY_H