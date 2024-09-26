package functions;

import functions.meta.*;
public final class Functions {

    // Приватный конструктор предотвращает создание экземпляров класса
    private Functions() {
        throw new UnsupportedOperationException("Instance creation is not allowed");
    }

    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power){
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2){
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2){
        return new Mult(f1, f2);
    }

    public static Function composition(Function f1, Function f2){
        return new Composition(f1, f2);
    }


    public static double integrate(Function func, double left, double right, double step){
        if (step <= 0) {
            throw new IllegalArgumentException("The sampling step must be positive.");
        }

        // Обеспечиваю, что a <= b
        double lower = Math.min(left, right);
        double upper = Math.max(left, right);

        // Проверка области определения функции на концах и промежуточных точках
        // Для метода трапеций необходимо, чтобы функция была определена во всех точках разбиения
        double current = lower;
        while (current<=upper){
            if (func.getLeftDomainBorder()>current && func.getRightDomainBorder()<current) {
                throw new IllegalArgumentException("The function is not defined at the point");
            }
            current+=step;
        }
        if (func.getLeftDomainBorder()>current && func.getRightDomainBorder()<current) {
                throw new IllegalArgumentException("The function is not defined at the point");
            }

        double integral = 0.0;
        double x0 = lower;
        while (x0 < upper){
            double x1 = x0 + step;
            if (x1 > upper) x1 = upper;
            double y0 = func.getFunctionValue(x0);
            double y1 = func.getFunctionValue(x1);

            double width = x1 - x0;
            // Площадь трапеции: (y0 + y1) / 2 * width
            integral += (y0 + y1) * width / 2.0;
            x0 = x1;
        }
        return (left>right) ? -integral : integral;
    }
}
