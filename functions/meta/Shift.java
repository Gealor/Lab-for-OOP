package functions.meta;

import functions.Function;

public class Shift implements Function {
    private final Function baseFunction;
    private final double shiftX;
    private final double shiftY;

    public Shift(Function baseFunction, double shiftX, double shiftY) {
        this.baseFunction = baseFunction;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    @Override
    public double getLeftDomainBorder() {
        return baseFunction.getLeftDomainBorder() + shiftX;
    }

    @Override
    public double getRightDomainBorder() {
        return baseFunction.getRightDomainBorder() + shiftX;
    }

    @Override
    public double getFunctionValue(double x) {
        return baseFunction.getFunctionValue(x - shiftX) + shiftY;
    }
}
