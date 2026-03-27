#include <cmath>
#include "armstrong_numbers.h"

namespace armstrong_numbers
{
    bool is_armstrong_number(int num)
    {
        if (num == 0) return true;

        int num_digits = std::log10(num) + 1;
        int sum = 0;

        for (int rem = num; rem != 0; rem /= 10)
        {
            sum += std::pow(rem % 10, num_digits);
        }

        return sum == num;
    }
}