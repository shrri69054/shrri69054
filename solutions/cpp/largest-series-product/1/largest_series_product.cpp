#include "largest_series_product.h"
#include <stdexcept>
#include <algorithm>

namespace largest_series_product {

// TODO: add your solution here
    long long largest_product(std::string str, long unsigned int span)
    {
        if(span > str.size()) throw std::domain_error("");
        long long maxProd = 0;
        for(unsigned int sPos = 0;sPos <= (str.size()-span);sPos++)
        {
            long long prod=1;
            for(unsigned int i=0;i<span;i++)
            {
                if(!isdigit(str[sPos+i])) throw std::domain_error("");
                prod*= static_cast<unsigned char>(str[sPos+i]-'0');
            }
            if(prod > maxProd) maxProd = prod;
        }
        return maxProd;
    }
}  // namespace largest_series_product





