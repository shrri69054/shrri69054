#include "darts.h"

namespace darts {
int score (float x, float y){
    if(x*x+y*y<=1)return 10;
    else if (x*x+y*y<=25)return 5;
    else if (x*x+y*y<=100)return 1;
    return 0;
}
} // namespace darts



