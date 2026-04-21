#pragma once
#include <utility>

namespace queen_attack
{
    class chess_board
    {
    private:
        // Member variables
        std::pair<int, int> white_;
        std::pair<int, int> black_;

    public:
        // Constructor
        chess_board(std::pair<int, int>, std::pair<int, int>);

        // Member functions
        std::pair<int, int> white() const;
        std::pair<int, int> black() const;
        bool can_attack() const;
    };
} // namespace queen_attack
