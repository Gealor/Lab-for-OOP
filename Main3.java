import functions.ArrayTabulatedFunction;
import functions.FunctionPoint;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.exceptions.InappropriateFunctionPointException;
import functions.exceptions.InappropriateFunctionPointException;

public class Main3 {
    public static void main(String[] args) {
        try {
            double[] yValues1 = {-8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0};
            double[] yValues2 = {-8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0};
            TabulatedFunction tfA1 = new ArrayTabulatedFunction(-4.0, 4.0, yValues1);

            TabulatedFunction tfL2 = new LinkedListTabulatedFunction(-4.0, 4.0, yValues2);
            System.out.println("\nМассив");
            System.out.println(tfA1.toString());
            System.out.println(tfA1.hashCode());

            System.out.println("\nСвязанный список");
            System.out.println(tfL2.toString());
            System.out.println(tfL2.hashCode());

            System.out.println("\nПроверка эквивалентности между массивом и списком");
            System.out.println(tfA1.equals(tfL2));
            System.out.println(tfL2.equals(tfA1));

            System.out.println("\nПроверка эквивалентности между массивом и массивом");
            double[] yValues3 = {-8.0, -6.0, -4.0, -2.0, 1.0, 2.0, 4.0, 6.0, 8.0};
            TabulatedFunction tfA3 = new ArrayTabulatedFunction(-4.0, 4.0, yValues3);
            System.out.println(tfA1.equals(tfA3));

            System.out.println("\nПроверка эквивалентности между списком и списком");
            double[] yValues4 = {-8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0};
            TabulatedFunction tfL4 = new LinkedListTabulatedFunction(-4.0, 4.0, yValues4);
            System.out.println(tfL2.equals(tfL4));

            System.out.println("\nХэш-коды");
            FunctionPoint[] points1 = new FunctionPoint[]{ new FunctionPoint(-1, 1), new FunctionPoint(0,0),
                new FunctionPoint(1,1)};
            FunctionPoint[] points2 = new FunctionPoint[]{ new FunctionPoint(-1, 1), new FunctionPoint(1,1)};
            double[] yValues5 = {-8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0, 8.0};
            TabulatedFunction tfA5 = new ArrayTabulatedFunction(-4.0, 4.0, yValues5);
            TabulatedFunction tfL5 = new LinkedListTabulatedFunction(-4.0, 4.0, yValues5);
            System.out.println(tfA5.hashCode());
            System.out.println(tfL5.hashCode());

            TabulatedFunction tfA6 = new ArrayTabulatedFunction(points1);
            TabulatedFunction tfL6 = new LinkedListTabulatedFunction(points2);
            System.out.println(tfA6.hashCode());
            System.out.println(tfL6.hashCode());


            System.out.println("\nКлонирование массива");
            System.out.println("tfA5 " + tfA5.toString());
            System.out.println("tfL5 " + tfL5.toString());

            TabulatedFunction tfACopy = tfA5.clone();
            TabulatedFunction tfLCopy = tfL5.clone();

            System.out.println("\ntfA5Copy " + tfACopy.toString());
            System.out.println("tfL5Copy " + tfLCopy.toString());

            tfA5.addPoint(new FunctionPoint(3.9, 7));
            tfL5.addPoint(new FunctionPoint(3.9, 7));

            System.out.println("\ntfA5     " + tfA5.toString());
            System.out.println("tfA5Copy " + tfACopy.toString());

            System.out.println("\ntfL5     " + tfL5.toString());
            System.out.println("tfL5Copy " + tfLCopy.toString());

        } catch (InappropriateFunctionPointException e) { System.out.println("Exception" + e); }
    }
}
