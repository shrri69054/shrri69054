#pragma once

namespace complex_numbers
{
    class Complex
    {
    private:
        double _real;
        double _imag;

    public:
        // Constructor
        Complex(double, double i = 0.0);

        // Getters
        double real() const;
        double imag() const;

        // Methods
        double abs() const;
        Complex conj() const;
        Complex exp() const;

        // Operators
        // 5.0 + complex_number works because of
        // friend Complex operator+(double, const Complex &);
        // complex_number + 5.0 works because we are allowing implicit conversion
        // from a double value as the real part
        // with default value of 0.0 for the imag value in the constructor
        // If we want to prevent implicit conversion from double to Complex
        // we can make the constructor explicit and add a third friend function
        // for each of the operations, eg:
        // friend Complex operator+(const Complex &, double);

        // Addition
        friend Complex operator+(const Complex &, const Complex &);
        friend Complex operator+(double, const Complex &);

        // Subtraction
        friend Complex operator-(const Complex &, const Complex &);
        friend Complex operator-(double, const Complex &);

        // Multiplication
        friend Complex operator*(const Complex &, const Complex &);
        friend Complex operator*(double, const Complex &);

        // Division
        friend Complex operator/(const Complex &, const Complex &);
        friend Complex operator/(double, const Complex &);
    };
} // namespace complex_numbers
