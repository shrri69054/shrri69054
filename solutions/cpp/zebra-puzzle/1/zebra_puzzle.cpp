#include "zebra_puzzle.h"

#include <array>
#include <algorithm>
#include <cmath>
#include <string>

constexpr int N = 5;

enum { ENGLISH, SPANIARD, UKRAINIAN, NORWEGIAN, JAPANESE };
enum { RED, GREEN, IVORY, YELLOW, BLUE };
enum { DOG, SNAIL, FOX, HORSE, ZEBRA };
enum { COFFEE, TEA, MILK, JUICE, WATER };
enum { READING, DANCING, PAINTING, FOOTBALL, CHESS };

static const std::array<std::string, N> RESIDENTS = {
    "Englishman", "Spaniard", "Ukrainian", "Norwegian", "Japanese"
};

static int owner_of(const std::array<int, N>& resident_to_house, int house)
{
    for (int i = 0; i < N; ++i)
        if (resident_to_house[i] == house)
            return i;
    return -1;
}

namespace zebra_puzzle {

Solution solve()
{
    Solution solution{};

    std::array<int, N> color   = {0,1,2,3,4},
                       resident= {0,1,2,3,4},
                       pet     = {0,1,2,3,4},
                       drink   = {0,1,2,3,4},
                       hobby   = {0,1,2,3,4};

    do {
        // Colors
        if (std::abs(color[GREEN] - color[IVORY]) != 1)
            continue;

        do {
            // Residents
            if (resident[NORWEGIAN] != 0) continue;
            if (std::abs(resident[NORWEGIAN] - color[BLUE]) != 1) continue;
            if (resident[ENGLISH] != color[RED]) continue;

            do {
                // Pets
                if (resident[SPANIARD] != pet[DOG]) continue;

                do {
                    // Drinks
                    if (drink[COFFEE] != color[GREEN]) continue;
                    if (resident[UKRAINIAN] != drink[TEA]) continue;
                    if (drink[MILK] != 2) continue;

                    do {
                        // Hobbies
                        if (pet[SNAIL] != hobby[DANCING]) continue;
                        if (color[YELLOW] != hobby[PAINTING]) continue;
                        if (hobby[FOOTBALL] != drink[JUICE]) continue;
                        if (resident[JAPANESE] != hobby[CHESS]) continue;
                        if (std::abs(hobby[READING] - pet[FOX]) != 1) continue;
                        if (std::abs(hobby[PAINTING] - pet[HORSE]) != 1) continue;

                        // Solution found
                        solution.drinksWater = RESIDENTS[owner_of(resident, drink[WATER])];
                        solution.ownsZebra = RESIDENTS[owner_of(resident, pet[ZEBRA])];

                        return solution;

                    } while (std::next_permutation(hobby.begin(), hobby.end()));

                } while (std::next_permutation(drink.begin(), drink.end()));

            } while (std::next_permutation(pet.begin(), pet.end()));

        } while (std::next_permutation(resident.begin(), resident.end()));

    } while (std::next_permutation(color.begin(), color.end()));

    return solution;
}

}  // namespace zebra_puzzle





