#include "pascals_triangle.h"



namespace pascals_triangle {
    std::vector<std::vector<int>> generate_rows(int total_rows) {
        std::vector<std::vector<int>> triangle{};
        for(int row{}; row < total_rows; ++row) {
            std::vector<int> line{};
            if (row > 0) line.emplace_back(1);
            for (int column{}; column < row - 1; ++column) {
                int sum{triangle.at(row-1).at(column) + triangle.at(row-1).at(column+1)};
                line.emplace_back(sum);
            }
            
            line.emplace_back(1);
            triangle.emplace_back(line);
        }
        return triangle;
    }
}  // namespace pascals_triangle




