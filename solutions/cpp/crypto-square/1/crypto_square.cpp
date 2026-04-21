#include "crypto_square.h"
#include <string>
#include <algorithm>
#include <cctype>
#include <cmath>
namespace crypto_square {
    namespace {
        std::string normalize(std::string input) {
        // function to lower characters
            auto to_lower = [](char c) {
                return std::tolower(static_cast<unsigned char>(c));
            };
            // function to check if character is alnum
            auto is_not_alnum = [](char c) {
                return !std::isalnum(static_cast<unsigned char>(c));
            };
            input.erase(
                std::remove_if(input.begin(), input.end(), is_not_alnum),
                input.end()
            );
            // next convert all lowercase letters
            std::transform(input.begin(), input.end(), input.begin(), to_lower);
            return input;
    } // end of normalize()
        std::string make_chunks(const std::string& normalized, size_t r, size_t c, size_t L) {
            // index = row_number * number of columns (c) + column_number
            std::string result;
            for (size_t col = 0; col < c; ++col) {
                std::string chunk;
                for (size_t row = 0; row < r; ++row) {
                    size_t index = (row * c) + col;
                    if (index < L) { // if within bounds
                        chunk += normalized[index];
                    } else {
                        chunk += ' '; // append a space
                    }
                }
                result += chunk + " ";
            }
            return result;
        } // end of make_chunks()
    } // anonymous namespace
    // constructor
    cipher::cipher(const std::string& input)
        : raw_(input) {}
    std::string cipher::normalized_cipher_text() const {
        // first test passed - works for empty and "nonsense" input
        std::string norm = normalize(raw_);
        // second test -- make the chunks
        size_t L = norm.length();
        if (L == 0) return "";
        size_t c = std::ceil(std::sqrt(L));
        size_t r = std::ceil(static_cast<double>(L) / c);
        std::string out = make_chunks(norm, r, c, L);
        if (!out.empty() && out.back() == ' ')
            out.pop_back();
        return out;
    }
}  // namespace crypto_square




