#include "complex_numbers.h"
#include <cmath>
#include <stdexcept>

namespace complex_numbers
{
    // Constructor
    Complex::Complex(double r, double i) : _real(r), _imag(i) {}

    // Methods
    double Complex::real() const { return this->_real; }
    double Complex::imag() const { return this->_imag; }

    // Operators
    // Addition
    Complex operator+(const Complex &lhs, const Complex &rhs)
    {
        // (a + bi) + (c + di) = (a + c) + (b + d)i
        return Complex{lhs._real + rhs._real, lhs._imag + rhs._imag};
    }
    Complex operator+(double lhs, const Complex &rhs) { return rhs + lhs; }

    // Subtraction
    Complex operator-(const Complex &lhs, const Complex &rhs)
    {
        // (a + bi) - (c + di) = (a - c) + (b - d)i
        return Complex{lhs._real - rhs._real, lhs._imag - rhs._imag};
    }
    Complex operator-(double lhs, const Complex &rhs) { return Complex{lhs} - rhs; }

    // Multiplication
    Complex operator*(const Complex &lhs, const Complex &rhs)
    {
        // (a + bi) * (c + di) = (ac - bd) + (ad + bc)i
        return Complex{lhs._real * rhs._real - lhs._imag * rhs._imag,
                       lhs._real * rhs._imag + lhs._imag * rhs._real};
    }
    Complex operator*(double lhs, const Complex &rhs) { return rhs * lhs; }

    // Division
    Complex operator/(const Complex &lhs, const Complex &rhs)
    {
        // (a + bi) / (c + di) = (a + bi)(c - di) / (c^2 + d^2)
        // say e = c^2 + d^2
        // (a + bi) / (c + di) = ((a + bi) * (c - di))/e
        double denominator = rhs._real * rhs._real + rhs._imag * rhs._imag;
        if (denominator == 0.0)
            throw std::domain_error("Denominator has 0 magnitude.");

        // Numerator = (a + bi) * (c - di)
        Complex numerator = lhs * rhs.conj();
        return Complex{numerator._real / denominator, numerator._imag / denominator};
    }
    Complex operator/(double lhs, const Complex &rhs) { return Complex{lhs} / rhs; }

    // Methods
    double Complex::abs() const { return std::hypot(this->_real, this->_imag); }
    Complex Complex::conj() const { return Complex{this->_real, -this->_imag}; }

    // e ^ (a + bi) = e^a * e^bi = e^a (cos(b) + isin(b))
    Complex Complex::exp() const { return std::exp(this->_real) * Complex{std::cos(this->_imag), std::sin(this->_imag)}; }
} // namespace complex_numbers





