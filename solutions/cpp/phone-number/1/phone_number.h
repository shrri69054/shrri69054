#pragma once
#include <string>
#include <stdexcept>
#include <algorithm>

namespace phone_number {
    class phone_number {
        public:
            // declare your constructor
            phone_number(const std::string& input);
            std::string number() const; // does not mutate the object!
        private:
            std::string digits; // this is the cleaned digits
    };
}  // namespace phone_number
