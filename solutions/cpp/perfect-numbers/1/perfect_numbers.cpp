#include "perfect_numbers.h"
#include <stdexcept>

namespace perfect_numbers {
    classification classify(int number) {
        if (number <= 0) {
            throw std::domain_error("Number must be a positive integer.");
        }

        int sum = 0;
        for (int i = 1; i <= number / 2; ++i) {
            if (number % i == 0) {
                sum += i;
            }
        }

        if (sum == number) {
            return classification::perfect;
        } else if (sum < number) {
            return classification::deficient;
        } else {
            return classification::abundant;
        }
    }
}





