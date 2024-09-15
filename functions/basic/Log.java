package functions.basic;

import functions.Function;

public class Log implements Function {
    // основание логарифма(неизменяемый объект)
    private final double base;

    public Log(double base) {
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("The base of the logarithm must be greater than zero and not equal to one.");
        }
        this.base = base;
    }

    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("The argument of the logarithm must be positive.");
        }
        // https://www.geeksforgeeks.org/how-to-calculate-log-base-2-of-an-integer-in-java/ (полезная ссылка, про вычисление логирифма по произвольному основанию на Java)
        return Math.log(x) / Math.log(base);
    }
}
