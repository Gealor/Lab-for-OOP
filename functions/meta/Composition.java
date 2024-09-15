package functions.meta;

import functions.Function;

public class Composition implements Function {
    private final Function outerFunction;
    private final Function innerFunction;

    public Composition(Function outerFunction, Function innerFunction) {
        this.outerFunction = outerFunction;
        this.innerFunction = innerFunction;
    }

    @Override
    public double getLeftDomainBorder() {
        return innerFunction.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return innerFunction.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return outerFunction.getFunctionValue(innerFunction.getFunctionValue(x));
    }
}