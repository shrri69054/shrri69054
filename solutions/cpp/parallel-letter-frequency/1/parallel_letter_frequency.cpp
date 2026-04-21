#include "parallel_letter_frequency.h"
#include <iostream>
#include <vector>
#include <unordered_map>
#include <thread>
#include <mutex>
#include <string_view>
#include <cctype>

namespace parallel_letter_frequency {

std::unordered_map<char, int> sharedMap;
std::mutex mapMutex;  

void get_frequency(std::basic_string_view<char> str) {
    std::lock_guard<std::mutex> lock(mapMutex);
    for (char c : str) {
        if (std::isalpha(c)) {   
            c = std::tolower(c);
            sharedMap[c]++;
        }    
    }
}

std::unordered_map<char, int> frequency(const std::vector<std::string_view> strings) {  
    sharedMap.clear();
    std::vector<std::thread> threads;
    for (const auto str : strings) {
        threads.push_back(std::thread(get_frequency, str));
    }
    for (auto& thread : threads) {
        thread.join();
    }
    return sharedMap;
}

}





