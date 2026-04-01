#include "anagram.h"
#include <cctype>

namespace {

    std::string normalize(std::string_view str) {
        std::string norm;
        norm.reserve(str.size());
        for (unsigned char c : str) {
            norm.push_back(std::tolower(c));
        }
        return norm;
    }

    std::array<int, 26> get_frequency(std::string_view str) {
        std::array<int, 26> freq{};
        for (unsigned char c : str) {
            if (std::isalpha(c)) freq[std::tolower(c) - 'a']++;
        }
        return freq;
    }

} // namespace


namespace anagram {

    anagram::anagram(std::string_view word) {
        word_ = normalize(word);
        freq_ = get_frequency(word_);
    }
    
    std::vector<std::string> anagram::matches(const std::vector<std::string>& candidates) const {
        std::vector<std::string> ret;
        ret.reserve(candidates.size());
        for (const std::string& cand : candidates) {
            if (word_ == normalize(cand)) continue;
            if (freq_ == get_frequency(cand)) ret.push_back(cand);
        }
        return ret;
    }

} // namespace anagram





