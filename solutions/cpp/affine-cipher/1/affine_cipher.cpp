#include "affine_cipher.h"
#include <string>
#include <numeric>
#include <cctype>
#include <stdexcept>

namespace affine_cipher {

// TODO: add your solution here

const int m = 26;

bool coprime_check(int a) {
    return (std::gcd(a,m) == 1);
}

int mmi(int a) {
    if (!coprime_check(a)) {
        throw std::invalid_argument("Invalid syntax.");
    }

    for (int i_id {1}; i_id < m; ++i_id) {
        if (a * i_id % m == 1) {return i_id;}
    }

    throw std::invalid_argument("Invalid syntax.");
}

std::string encode(std::string sentence, int a, int b) {
    if (!coprime_check(a)) {
        throw std::invalid_argument("Invalid syntax.");
    }

    std::string new_sentence {};
    int i {};
    int e_x {};
    int counter {0};
    
    for (char c : sentence) {
        c = std::tolower(c);
        if (c >= 48 && c <= 57) {
            if(counter >= 5) {new_sentence += ' '; counter = 0;}
            new_sentence += c;
            ++counter;
        }
        if (c >= 97 && c <= 122) {
            if(counter >= 5) {new_sentence += ' '; counter = 0;}
            i   = c - 97;
            e_x = (a * i + b) % m;
            new_sentence += ('a' + e_x);
            ++counter;
        }
    }

    return new_sentence;
}

std::string decode(std::string sentence, int a, int b) {
    if (!coprime_check(a)) {
        throw std::invalid_argument("Invalid syntax.");
    }

    std::string new_sentence {};
    int y {};
    int d_y {};
    int am1 {mmi(a)};
    int y_offset {};
    
    for (char c : sentence) {
        c = std::tolower(c);
        if (c >= 48 && c <= 57) {
            new_sentence += c;
        }
        if (c >= 97 && c <= 122) {
            y   = c - 97;
            y_offset = y - b;
            while (y_offset < 0) {
                y_offset += m;
            }
            
            d_y = (am1 * y_offset) % m;
            new_sentence += ('a' + d_y);
        }
    }

    return new_sentence;
}

}  // namespace affine_cipher




