package functions;

public interface TabulatedFunctionFactory {
    public TabulatedFunction createTabulatedFunction(double left, double right, int pointsCount);
    public TabulatedFunction createTabulatedFunction(double left, double right, double[] points);
    public TabulatedFunction createTabulatedFunction(FunctionPoint[] points);
}
