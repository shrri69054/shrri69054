#include "flower_field.h"
#include <array>
#include <utility>

namespace flower_field {

// TODO: add your solution here
static constexpr std::array<std::pair<int, int>, 8> DIRS{{
    {-1, -1},
    {-1, 0},
    {-1, 1},
    {0, -1},
    {0, 1},
    {1, -1},
    {1, 0},
    {1, 1},
}};
int neighbors(const std::vector<std::string> &field, int x, int y, int row, int col)
{
    int count{};
    for (auto [dx, dy] : DIRS) {
        int nx = x + dx;
        int ny = y + dy;
        if (nx >= 0 && ny >= 0 && nx < row && ny < col && field[nx][ny] == '*') ++count;
    }
    return count;
}

std::vector<std::string> annotate(const std::vector<std::string> &board)
{
    int row = static_cast<int>(board.size());
    int col = row > 0 ? static_cast<int>(board[0].length()) : 0;
    std::vector<std::string> field{board};
    for (int i = 0; i < row; ++i) {
        for (int j = 0; j < col; ++j) {
            if (board[i][j] == '*') continue;
            int n = neighbors(board, i, j, row, col);
            field[i][j] = n > 0 ? '0' + n : ' ';
        }
    }
    return field;
}

} // namespace flower_field





