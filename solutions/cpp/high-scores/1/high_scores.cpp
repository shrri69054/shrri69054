#include "high_scores.h"
#include <algorithm>
namespace arcade {
std::vector<int> HighScores::list_scores() {
    return scores;
}
int HighScores::latest_score() {
    return scores.back();
}
int HighScores::personal_best() {
    std::vector<int>::iterator max =std::max_element(scores.begin(), scores.end());
    return *max;
}
std::vector<int> HighScores::top_three() {
    std::vector<int> copy{scores};
    std::sort(copy.begin(), copy.end(), std::greater<int>());
    if (copy.size()<3){return copy;}
    return std::vector<int>(copy.begin(), copy.begin() + 3);
}
}  // namespace arcade
