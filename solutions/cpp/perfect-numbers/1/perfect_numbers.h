#ifndef PERFECT_NUMBERS_H
#define PERFECT_NUMBERS_H

namespace perfect_numbers {
    enum classification {
        deficient,
        perfect,
        abundant,
        rejected // Add the rejected value
    };

    classification classify(int number);
}

#endif // PERFECT_NUMBERS_H
