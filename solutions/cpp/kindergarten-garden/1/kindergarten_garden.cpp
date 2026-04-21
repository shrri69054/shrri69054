#include "kindergarten_garden.h"
#include <unordered_map>
#include <vector>

namespace kindergarten_garden {

std::unordered_map<char, Plants> cups{
    {'G', Plants::grass},
    {'C', Plants::clover},
    {'R', Plants::radishes},
    {'V', Plants::violets}
};

std::array<Plants, 4> plants(std::string cups_input, std::string child) {
    std::vector<std::vector<char>> rows;
    std::vector<char> current;

    int index = (child[0] - 'A') * 2;

    for (char c : cups_input) {
        if (c == '\n') {
            rows.push_back(current);
            current.clear();
        } else {
            current.push_back(c);
        }
    }
    rows.push_back(current);

    return {
        cups[rows[0][index]],
        cups[rows[0][index + 1]],
        cups[rows[1][index]],
        cups[rows[1][index + 1]]
    };
}

}  // namespace kindergarten_garden