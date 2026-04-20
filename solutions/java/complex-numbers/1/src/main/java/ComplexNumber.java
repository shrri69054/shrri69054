public class ComplexNumber {



    private final double real;

    private final double imag;



    public ComplexNumber(final double real, final double imag) {

        this.real = real;

        this.imag = imag;

    }



    public double getReal() { return real; }



    public double getImaginary() { return imag; }



    public double abs() {

        return Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));

    }



    public ComplexNumber add(ComplexNumber c) {

        return new ComplexNumber(real + c.real, imag + c.imag); 

    }

    

    public ComplexNumber subtract(ComplexNumber c) {

        return new ComplexNumber(real - c.real, imag - c.imag); 

    }



    public ComplexNumber multiply(ComplexNumber c) {

        double rx = real, ix = imag, 

               ry = c.real, iy = c.imag;

        return new ComplexNumber(rx * ry - ix * iy, rx * iy + ry * ix);

    }



    public ComplexNumber divide(ComplexNumber c) {

        double rx = real, ix = imag, 

               ry = c.real, iy = c.imag;

        double d = Math.pow(c.abs(), 2);

        return new ComplexNumber(

                    (rx * ry + ix * iy) / d, (ix * ry - rx * iy) / d

                    );

    }



    public ComplexNumber conjugate() {

        return new ComplexNumber(real, -imag);

      }



    public ComplexNumber exponentialOf() {

        return 

            new ComplexNumber(

                    Math.exp(real) * Math.cos(imag), 

                    Math.exp(real) * Math.sin(imag)

                );

      }   

}