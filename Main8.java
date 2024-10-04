import functions.ArrayTabulatedFunction;
import functions.FunctionPoint;
import functions.TabulatedFunction;
import functions.TabulatedFunctionFactory;

public class Main8 {
    public static void main(String[] args) {
        try {
            double[] yValues = {-8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0 ,8.0};

//            // Имя класса, который необходимо создать
//            String className = "functions.LinkedListTabulatedFunction";
//
//            // Типы параметров конструктора (например, int для начальной емкости)
//            Class<?>[] paramTypes = { double.class, double.class, double[].class };
//
//            // Значения аргументов конструктора (например, начальная емкость = 10)
//            Object[] initargs = { -4, 4, yValues };
//
//            // Создание объекта TabulatedFunction через фабричный метод
//            TabulatedFunction f = TabulatedFunctionFactory.createTabulatedFunction(className, paramTypes, initargs);
//
//
            TabulatedFunction f = new ArrayTabulatedFunction(-4, 4, yValues);
            // Использование улучшенного цикла for-each
            System.out.println("--- Пример использования улучшенного цикла for -------------------");
            for (FunctionPoint p : f) {
                System.out.println(p);
            }
            System.out.println("------------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
