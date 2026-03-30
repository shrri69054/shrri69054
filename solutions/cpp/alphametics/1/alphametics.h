#ifndef ALPHAMETICS_H
#define ALPHAMETICS_H

#include <optional>
#include <string_view>
#include <unordered_map>

namespace alphametics {

std::optional<std::unordered_map<char, int>> solve(std::string_view puzzle);

}  // namespace alphametics

#endif  // ALPHAMETICS_H