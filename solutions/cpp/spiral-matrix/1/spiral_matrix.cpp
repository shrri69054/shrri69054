#include "spiral_matrix.h"

#include <cstddef>
#include <cstdint>
#include <vector>

namespace spiral_matrix {
    using matrixType = std::vector<std::vector<uint32_t>>; //per the test cases

    namespace /*implementation*/{

        //this algorithm will fill spiral matrices of any size, not just square ones
        void fill_implementation (matrixType& m, int32_t from_i, int32_t from_j, int32_t to_i, int32_t to_j, uint32_t currentValue = 1){
            //branch on the direction to-from
            if(from_i <= to_i){ //downward
                if(from_j <= to_j){ //down right => write a strip rightward
                    for(int32_t j = from_j; j <= to_j; ++j){
                        m[from_i][j] = currentValue++;
                    }
                    if(from_i + 1 <= to_i){ //recursion if there is matrix left after trimming the written strip
                       fill_implementation(m, from_i + 1, to_j, to_i, from_j, currentValue);
                    }                   
                } else { //down left => write a strip downward       
                    for(int32_t i = from_i; i <= to_i; ++i){
                        m[i][from_j] = currentValue++;
                    }   
                    if(from_j >= to_j + 1){ //recursion if there is matrix left after trimming the written strip
                       fill_implementation(m, to_i, from_j - 1, from_i, to_j, currentValue);
                    }                                       
                }     
            } else { //upward
                if(from_j <= to_j){ //up right => write a strip upward
                    for(int32_t i = from_i; i >= to_i; --i){
                        m[i][from_j] = currentValue++;
                    }    
                    if(from_j + 1 <= to_j){ //recursion if there is matrix left after trimming the written strip
                       fill_implementation(m, to_i, from_j + 1, from_i, to_j, currentValue);
                    }                  
                } else { //up left => write a strip leftward
                    for(int32_t j = from_j; j >= to_j; --j){
                        m[from_i][j] = currentValue++;
                    }     
                    if(from_i >= to_i + 1){ //recursion if there is matrix left after trimming the written strip
                       fill_implementation(m, from_i - 1, to_j, to_i, from_j, currentValue);
                    }                                 
                }                
            }
        }
    } //namespace implementation
    
    matrixType spiral_matrix(uint64_t n){
        if(n == 0) return {};
        //allocate matrix
        matrixType m{};    
        m.resize(n);
        for(size_t i = 0; i < n; ++i) m[i].resize(n);
        //fill
        fill_implementation(m, 0,0, n-1,n-1);   
        //return
        return m;
    }
}  // namespace spiral_matrix





