import functions.*;
import functions.exceptions.InappropriateFunctionPointException;
public class Main {
    public static void main(String[] args) {
        // Массив
        System.out.println("\nМассив\n");
        try {
            double[] yValues = {-8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0 ,8.0};
            TabulatedFunction tf1 = new ArrayTabulatedFunction(-4.0, 4.0, yValues);
            for (FunctionPoint point : tf1.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("Left border: " + tf1.getLeftDomainBorder());
            System.out.println("Right border: " + tf1.getRightDomainBorder());
            double x = 1.5;
            System.out.println("Value of function for " + x + " : " + tf1.getFunctionValue(x));
            System.out.println("Count of dots: " + tf1.getPointsCount());

            for (int index = 0; index< tf1.getPointsCount(); index++){
                FunctionPoint point=tf1.getPoint(index);
                System.out.println("Dot with getPoint method : " + "( " + point.getX() + " : " + point.getY() + " )");
            }

            tf1.setPoint(4, new FunctionPoint(-0.5,0));
            for (FunctionPoint point : tf1.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("X Coordinate for index " + 4 + " : " + tf1.getPointX(4));
            System.out.println("Y Coordinate for index " + 4 + " : " + tf1.getPointY(4));


            tf1.deletePoint(4);
            for (FunctionPoint point : tf1.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("Count of dots: " + tf1.getPointsCount());


            tf1.addPoint(new FunctionPoint(0,0));
            for (FunctionPoint point : tf1.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("Count of dots: " + tf1.getPointsCount());
        } catch (InappropriateFunctionPointException e) { System.out.println("Exception" + e); }

        // Связанный список
        System.out.println("\nСвязанный список\n");

        try {
            double[] yValues = {-8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0 ,8.0};
            TabulatedFunction tf = new LinkedListTabulatedFunction(-4.0, 4.0, yValues);
            for (FunctionPoint point : tf.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("Left border: " + tf.getLeftDomainBorder());
            System.out.println("Right border: " + tf.getRightDomainBorder());
            double x = 1.5;
            System.out.println("Value of function for " + x + " : " + tf.getFunctionValue(x));
            System.out.println("Count of dots: " + tf.getPointsCount());

            for (int index = 0; index< tf.getPointsCount(); index++){
                FunctionPoint point=tf.getPoint(index);
                System.out.println("Dot with getPoint method : " + "( " + point.getX() + " : " + point.getY() + " )");
            }

            tf.setPoint(4, new FunctionPoint(-0.5,0));
            for (FunctionPoint point : tf.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("X Coordinate for index " + 4 + " : " + tf.getPointX(4));
            System.out.println("Y Coordinate for index " + 4 + " : " + tf.getPointY(4));


            tf.deletePoint(4);
            for (FunctionPoint point : tf.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("Count of dots: " + tf.getPointsCount());


            tf.addPoint(new FunctionPoint(0,0));
            for (FunctionPoint point : tf.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("Count of dots: " + tf.getPointsCount());

            // Конструктор по заданному кол-ву точек
            System.out.println("\nConstructor with specified number of points");
            FunctionPoint[] points = new FunctionPoint[] {new FunctionPoint(0, 1), new FunctionPoint(1, 2), new FunctionPoint(2,3)};
            TabulatedFunction tf2 = new LinkedListTabulatedFunction(points);
            for (FunctionPoint point : tf2.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
        } catch (InappropriateFunctionPointException e) { System.out.println("Exception" + e); }
    }
}
