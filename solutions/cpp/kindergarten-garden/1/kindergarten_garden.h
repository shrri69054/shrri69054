#pragma once

#include <string>
#include <array>

namespace kindergarten_garden {

enum class Plants {
    grass,
    clover,
    radishes,
    violets
};

std::array<Plants, 4> plants(std::string cups, std::string child);

}  // namespace kindergarten_garden