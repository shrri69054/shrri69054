#pragma once

#include <array>
#include <string_view>

namespace yacht {

using DiceT = std::array<int, 5>;

int score(DiceT, std::string_view);

}  // namespace yacht
