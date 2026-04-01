#pragma once
#include <string>
#include <string_view>
#include <vector>
#include <array>

namespace anagram {
    
    class anagram {
        private:
            std::string word_;
            std::array<int,26> freq_;
        public:
            anagram(std::string_view word);
            std::vector<std::string> matches(const std::vector<std::string>& words) const;
    };
    
}  // namespace anagram
