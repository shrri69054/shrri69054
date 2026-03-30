#include "queen_attack.h"
#include <stdexcept>

namespace queen_attack
{
    chess_board::chess_board(std::pair<int, int> w, std::pair<int, int> b) : white_(w), black_{b}
    {
        // The index values in the pair objects shouldn't be negative
        if (w.first < 0 || w.second < 0 || b.first < 0 || b.second < 0)
            throw std::domain_error("Queen positions must be positive.");

        // The index values should be less than 8
        if (w.first >= 8 || w.second >= 8 || b.first >= 8 || b.second >= 8)
            throw std::domain_error("Queen positions can't be greater than or equal to 8.");

        // The queens can't be in the same position
        if (w == b)
            throw std::domain_error("Queens can't be in the same position.");
    }

    // Member functions
    std::pair<int, int> chess_board::white() const { return this->white_; }
    std::pair<int, int> chess_board::black() const { return this->black_; }
    bool chess_board::can_attack() const
    {
        int row_diff = this->white_.first - this->black_.first;
        int col_diff = this->white_.second - this->black_.second;
        return row_diff == 0 || col_diff == 0 || row_diff == col_diff || row_diff + col_diff == 0;
    }
} // namespace queen_attack





