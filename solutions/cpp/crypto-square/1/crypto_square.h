#pragma once
#include <string>
#include <algorithm>
#include <cctype>
namespace crypto_square {
    class cipher {
        public:
            explicit cipher(const std::string& input); // the constructor
            std::string normalized_cipher_text() const; // will not modify object
        private:
            std::string raw_;     
    };
}  // namespace crypto_square
