#include "nth_prime.h"

#include <stdexcept>	
#include <vector>

namespace nth_prime {

    unsigned int nth(unsigned int num) {
        if (num < 1) {
            throw std::domain_error("");
        }
       std::vector<unsigned int> primes{2};
       for(unsigned int current{3}; primes.size() < num; current += 2) {
           bool is_prime{true};
           for(auto prime : primes) {
               if (current % prime == 0) {
                   is_prime = false;
                   break;
               }
           }
           if(is_prime) {
               primes.emplace_back(current);
           }
           
       }
        
        return primes.back();
    }

}  // namespace nth_prime





