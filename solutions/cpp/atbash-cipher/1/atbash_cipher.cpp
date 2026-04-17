#include "atbash_cipher.h"
#include <cctype>   // for isalpha, isdigit, tolower

namespace atbash_cipher {

std::string encode(std::string input) {
    std::string output;
    int counter = 0;

    for (char c : input) {
        if (std::isalpha(c)) {
            if (counter % 5 == 0 && counter > 0) output += ' ';
            output += 'z' - (std::tolower(c) - 'a');  // cleaner formula
            counter++;
        } 
        else if (std::isdigit(c)) {
            if (counter % 5 == 0 && counter > 0) output += ' ';
            output += c;
            counter++;
        }
    }
    return output;
}

std::string decode(std::string input) {
    std::string output;

    for (char c : input) {
        if (std::isdigit(c)) {
            output += c;
        } 
        else if (std::isalpha(c)) {
            output += 'z' - (std::tolower(c) - 'a');
        }
    }
    return output;
}

}