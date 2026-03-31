#pragma once

#include <string>
#include <vector>
#include <cmath>
#include <algorithm>
namespace secret_handshake {

    std::string toBinary(int num);
    std::vector<std::string> commands(int num);

}  // namespace secret_handshake



/*

1 mod 2 == 0 -> 1
0 mod 2

*/