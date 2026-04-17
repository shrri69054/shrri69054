#ifndef ATBASH_CIPHER_H
#define ATBASH_CIPHER_H

#include <string>

namespace atbash_cipher {

std::string encode(std::string input);
std::string decode(std::string input);

}

#endif