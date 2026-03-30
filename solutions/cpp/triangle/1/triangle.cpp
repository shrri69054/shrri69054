#include "triangle.h"

#include <algorithm>  // for std::max
#include <stdexcept>

namespace triangle {

void checkIfTriangleIsValid(double a, double b, double c) {
    double longestSide = std::max(a, std::max(b, c));
    double smallerSides = a + b + c - longestSide;

    if (a <= 0 || b <= 0 || c <= 0 || longestSide > smallerSides) {
        throw std::domain_error("This is not a valid triangle.");
    }
}

flavor kind(double a, double b, double c) {
    checkIfTriangleIsValid(a, b, c);

    if (a == b && b == c)
        return flavor::equilateral;

    if (a == b || a == c || b == c)
        return flavor::isosceles;

    return flavor::scalene;
}

} // namespace triangle