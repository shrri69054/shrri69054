#include "scrabble_score.h"

#include <map>
#include <cctype>

namespace scrabble_score {
    /*
        constexpr int[] get_scores() {
        int scores[26];
        scores['A' - 'A'] = 1;
        scores['E' - 'A'] = 1;
        scores['I' - 'A'] = 1;
        scores['O' - 'A'] = 1;
        scores['U' - 'A'] = 1;
        scores['L' - 'A'] = 1;
        scores['N' - 'A'] = 1;
        scores['R' - 'A'] = 1;
        scores['S' - 'A'] = 1;
        scores['T' - 'A'] = 1;
        scores['D' - 'A'] = 2;
        scores['G' - 'A'] = 2;
        scores['B' - 'A'] = 3;
        scores['C' - 'A'] = 3;
        scores['M' - 'A'] = 3;
        scores['P' - 'A'] = 3;
        scores['F' - 'A'] = 4;
        scores['H' - 'A'] = 4;
        scores['V' - 'A'] = 4;
        scores['W' - 'A'] = 4;
        scores['Y' - 'A'] = 4;
        scores['K' - 'A'] = 5;
        scores['J' - 'A'] = 8;
        scores['X' - 'A'] = 8;
        scores['Q' - 'A'] = 10;
        scores['Z' - 'A'] = 10;
        return scores;
    };

    constexpr int[] letter_scores2 = get_scores();
    */
    const std::map<char, int> letter_scores{
        {'A', 1},
        {'E', 1},
        {'I', 1},
        {'O', 1},
        {'U', 1},
        {'L', 1},
        {'N', 1},
        {'R', 1},
        {'S', 1},
        {'T', 1},
        {'D', 2},
        {'G', 2},
        {'B', 3},
        {'C', 3},
        {'M', 3},
        {'P', 3},
        {'F', 4},
        {'H', 4},
        {'V', 4},
        {'W', 4},
        {'Y', 4},
        {'K', 5},
        {'J', 8},
        {'X', 8},
        {'Q', 10},
        {'Z', 10}
    };

    int score(std::string_view word) {
        int score{};
        for (auto c : word) {
            score += letter_scores.at(std::toupper(c));
            //score += letter_scores2[std::toupper(c) - 'A'];
        }
        return score;
    }
}  // namespace scrabble_score




