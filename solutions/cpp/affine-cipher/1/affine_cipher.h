
#pragma once
#include <string>
#ifndef AFFINE_CIPHER_H
#define AFFINE_CIPHER_H

namespace affine_cipher {

// TODO: add your solution here
    
bool coprime_check(int a);
int mmi(int a);
std::string encode(std::string sentence, int a, int b);
std::string decode(std::string sentence, int a, int b);

}  // namespace affine_cipher

#endif  // AFFINE_CIPHER_H
