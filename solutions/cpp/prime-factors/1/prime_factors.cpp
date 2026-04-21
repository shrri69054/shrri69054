#include "prime_factors.h"
namespace prime_factors {
std::vector<long long> of(long long num){
    std::vector<long long> result;
    int i{2};
    
    if (num<2) {return result;}
    while (num>1){
        if (num%i==0){
            result.push_back(i);
            num /= i;
        } else {
            ++i;
        }
    }
    return result;
}
}  // namespace prime_factors
