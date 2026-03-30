#include "alphametics.h"

#include <algorithm>
#include <cctype>
#include <limits>
#include <numeric>
#include <string>
#include <vector>

namespace alphametics {

constexpr int kLetters = 27;

struct Exploration {
    std::vector<int> weight[kLetters];
    std::vector<std::size_t> rightmost =
        std::vector<std::size_t>(kLetters, std::numeric_limits<std::size_t>::max());
    std::vector<bool> leading =
        std::vector<bool>(kLetters, false);

    std::vector<int> ordering = std::vector<int>(kLetters);

    size_t mapping[kLetters]{};
    std::optional<std::unordered_map<char, int>> result =
        std::unordered_map<char, int>();

    Exploration(std::string_view puzzle) {
        size_t place = 0;
        int sign = -1;
        size_t previous = kLetters - 1;

        for (char c : std::string(puzzle.rbegin(), puzzle.rend())) {
            if (std::isupper(c)) {
                const size_t letter = c - 'A';

                weight[letter].reserve(place + 1);
                while (weight[letter].size() < place + 1) {
                    weight[letter].emplace_back(0);
                }

                weight[letter][place] += sign;

                if (rightmost[letter] > place) {
                    rightmost[letter] = place;
                }

                ++place;
                previous = letter;
            } else {
                leading[previous] = true;
                place = 0;

                if (c == '=') {
                    sign = 1;
                }
            }
        }
        leading[previous] = true;

        std::iota(ordering.begin(), ordering.end(), 0);
        std::stable_sort(ordering.begin(), ordering.end(),
                         [this](int a, int b) {
                             return rightmost[a] < rightmost[b];
                         });
    }

    bool validate() {
        int carry = 0;
        size_t place = 0;
        bool done = false;

        while (!done) {
            done = true;

            for (size_t k = 0; k < kLetters; ++k) {
                auto& w = weight[ordering[k]];
                if (w.size() > place) {
                    carry += w[place] * mapping[k];
                    done = false;
                }
            }

            if (carry % 10 != 0) {
                return false;
            }

            carry /= 10;
            place += 1;
        }

        return carry == 0;
    }

    bool search(size_t index, unsigned claimed, int carry, size_t place) {
        size_t letter = ordering[index];

        if (weight[letter].empty()) {
            return validate();
        }

        for (mapping[index] = 0; mapping[index] < 10; ++mapping[index]) {
            if (leading[letter] && mapping[index] == 0) {
                continue;
            }

            unsigned claimed2 = (claimed | (1 << mapping[index]));
            if (claimed == claimed2) {
                continue;
            }

            int carry2 = carry;
            size_t place2 = place;

            if (weight[ordering[index + 1]].empty() ||
                (rightmost[ordering[index + 1]] >
                 rightmost[ordering[index]])) {

                for (size_t k = 0; k <= index; ++k) {
                    auto& w = weight[ordering[k]];
                    if (w.size() > place)
                        carry2 += w[place] * mapping[k];
                }

                if (carry2 % 10 != 0) {
                    continue;
                }

                carry2 /= 10;
                place2 += 1;
            }

            (*result)[char(letter + 'A')] = mapping[index];

            if (search(index + 1, claimed2, carry2, place2))
                return true;
        }

        return false;
    }
};

std::optional<std::unordered_map<char, int>> solve(std::string_view puzzle) {
    std::optional<std::unordered_map<char, int>> result;

    Exploration exploration(puzzle);
    if (exploration.search(0, 0, 0, 0)) {
        result = exploration.result;
    }

    return result;
}

}  // namespace alphametics