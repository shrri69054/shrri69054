#include "yacht.h"
#include <algorithm>
#include <numeric>

namespace yacht {

int score(DiceT dice, std::string_view category)
{
    std::sort(dice.begin(), dice.end());

   // Map a face to the count
   std::array<int, 7> counts = {0};
   for (int d : dice) {
      ++counts[d];
   }

   if (category == "ones") {
       return 1 * counts[1];
   } else if (category == "twos") {
       return 2 * counts[2];
   } else if (category == "threes") {
       return 3 * counts[3];
   } else if (category == "fours") {
       return 4 * counts[4];
   } else if (category == "fives") {
       return 5 * counts[5];
   } else if (category == "sixes") {
       return 6 * counts[6];
   } else if (category == "full house") {
      if ((counts[dice.front()] == 3 && counts[dice.back()] == 2) || (counts[dice.front()] == 2 && counts[dice.back()] == 3)) {
         return counts[dice.front()] * dice.front() + counts[dice.back()] * dice.back();
      }
      return 0;
   } else if (category == "four of a kind") {
       if (counts[dice.front()] >= 4)
           return 4 * dice.front();
       if (counts[dice.back()] >= 4)
           return 4 * dice.back();
       return 0;
   } else if (category == "little straight") {
       for (int i = 0; i < static_cast<int>(dice.size()); ++i) {
           if (i + 1 != dice[i])
               return 0;
       }
       return 30;
   } else if (category == "big straight") {
       for (int i = 0; i < static_cast<int>(dice.size()); ++i) {
           if (i + 2 != dice[i])
               return 0;
       }
       return 30;
   } else if (category == "choice") {
       return std::accumulate(dice.begin(), dice.end(), 0);
   } else if (category == "yacht") {
       return counts[dice.front()] == 5 ? 50 : 0;
   }

   return 0;
}

}  // namespace yacht





