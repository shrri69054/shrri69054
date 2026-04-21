#include "eliuds_eggs.h"

namespace chicken_coop {

unsigned int positions_to_quantity(unsigned int positions)
{
    unsigned int count = 0;
    while (positions != 0)
    {
        if ((positions & 1) == 1) count++;
        positions >>= 1;
    }
    return count;
}

}  // namespace chicken_coop