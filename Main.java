import functions.*;
import functions.exceptions.InappropriateFunctionPointException;

public class Main {
    public static void main(String[] args) {
        // y = 2x
        double[] yValues = {-8.0, -6.0, -4.0, -2.0, 0.0, 2.0, 4.0, 6.0 ,8.0};
        TabulatedFunction tf = new TabulatedFunction(-4.0, 4.0, yValues);
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

        try{
            tf.setPoint(4, new FunctionPoint(-0.5,0));
        } catch (InappropriateFunctionPointException e){
            System.out.println(e.getMessage());
        }

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

        try {
            tf.addPoint(new FunctionPoint(0,0));
        } catch (InappropriateFunctionPointException e){
            System.out.println(e.getMessage());
        }

        for (FunctionPoint point : tf.getPoints()){
            System.out.println(point.getX() + " : " + point.getY());
        }
        System.out.println("Count of dots: " + tf.getPointsCount());

    }
}
