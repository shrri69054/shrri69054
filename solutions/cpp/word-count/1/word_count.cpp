#include "word_count.h"
#include <regex>

namespace word_count {

// TODO: add your solution here
constexpr const char *REGEX = "\\w+(?:'\\w+)?";
std::map<std::string, int> words(const std::string &sentence)
{
    std::map<std::string, int> counter{};
    std::regex re(REGEX);
    for (std::sregex_token_iterator it(sentence.begin(), sentence.end(), re), end; it != end; ++it) {
        std::string word{*it};
        std::transform(word.cbegin(), word.cend(), word.begin(), ::tolower);
        ++counter[word];
    }
    return counter;
}

} // namespace word_count





