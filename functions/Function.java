package functions;

public interface Function {
    public double getLeftDomainBorder();

    public double getRightDomainBorder();

    // Метод для получения значения функции в точке x
    public double getFunctionValue(double x);
}
