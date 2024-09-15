package functions.meta;

import functions.Function;

public class Scale implements Function {
    private final Function baseFunction;
    private final double scaleX;
    private final double scaleY;

    public Scale(Function baseFunction, double scaleX, double scaleY) {
        this.baseFunction = baseFunction;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public double getLeftDomainBorder() {
        if (scaleX == 0) {
            throw new IllegalArgumentException("The scaleX scaling factor cannot be zero.");
        }
        return baseFunction.getLeftDomainBorder() * scaleX;
    }

    @Override
    public double getRightDomainBorder() {
        if (scaleX == 0) {
            throw new IllegalArgumentException("Коэффициент масштабирования scaleX не может быть равен нулю.");
        }
        return baseFunction.getRightDomainBorder() * scaleX;
    }

    @Override
    public double getFunctionValue(double x) {
        return baseFunction.getFunctionValue(x / scaleX) * scaleY;
    }
}
