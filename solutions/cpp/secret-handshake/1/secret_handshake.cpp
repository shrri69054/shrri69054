#include "secret_handshake.h"
namespace secret_handshake {


    std::string toBinary(int num){
        std::string binary;
        while(num != 0){
            if( num % 2 == 0){
                binary.insert(0, "0");
            }
            else{
                binary.insert(0,"1");
            }
            num = std::floor(num / 2);
        }
        while(binary.size() <= 4){
            binary.insert(0,"0");
        }
        return binary;
    }

    std::vector<std::string> commands(int num){
        std::vector<std::string> handshake;
        std::string binary;
        binary = toBinary(num);
        if(binary[4] == '1'){
            handshake.push_back("wink");
        }
        if(binary[3] == '1'){
            handshake.push_back("double blink");
        }
        if(binary[2] == '1'){
            handshake.push_back("close your eyes");
        }
        if(binary[1] == '1'){
            handshake.push_back("jump");
        }
        if(binary[0] == '1'){
            std::reverse(handshake.begin(), handshake.end());
        }
        return handshake;
    }

}  // namespace secret_handshake





