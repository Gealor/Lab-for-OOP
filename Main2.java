import functions.basic.*;
import functions.*;
import functions.basic.TrigonometricFunction;

import java.io.*;

public class Main2{
    public static void main(String[] args) {
        // 1. Создание объектов синуса и косинуса и вывод их значений
        System.out.println("\n1. Создание объектов синуса и косинуса и вывод их значений");
        TrigonometricFunction objSin=new Sin();
        TrigonometricFunction objCos=new Cos();

        System.out.println("Sin values: ");
        int countSin = 0;
        for (double x=0; x<=Math.PI; x+=0.1){
            System.out.println(countSin + ". " + "Sin " + x + " = " + objSin.getFunctionValue(x));
            ++countSin;
        }

        System.out.println("\nCos values: ");
        int countCos = 0;
        for (double x=0; x<=Math.PI; x+=0.1){
            System.out.println(countCos + ". " + "Cos " + x + " = " + objCos.getFunctionValue(x));
            ++countCos;
        }

        // 2. Табулирование функций
        System.out.println("\n2. Табулирование функций");
        TabulatedFunction tabulatedSin = TabulatedFunctions.tabulate(objSin, 0, Math.PI, 10);
        TabulatedFunction tabulatedCos = TabulatedFunctions.tabulate(objCos, 0, Math.PI, 10);
        System.out.println("Sin values: ");
        int countSin2 = 0;
        for (double x=0; x<=Math.PI; x+=0.1){
            System.out.println(countSin2 + ". " + "Sin " + x + " = " + tabulatedSin.getFunctionValue(x));
            ++countSin2;
        }

        System.out.println("\nCos values: ");
        int countCos2 = 0;
        for (double x=0; x<=Math.PI; x+=0.1){
            System.out.println(countCos2 + ". " + "Cos " + x + " = " + tabulatedCos.getFunctionValue(x));
            ++countCos2;
        }

        // 3. Сумма квадратов функций
        System.out.println("\n3. Сумма квадратов функций");
        Function squaresSin = Functions.power(objSin, 2);
        Function squaresCos= Functions.power(objCos, 2);
        Function sumOfSquaresFunction = Functions.sum(squaresSin, squaresCos);
        int countCos3 = 0;
        System.out.println("Sum Squares values: ");
        for (double x=0; x<=Math.PI; x+=0.1){
            System.out.println(countCos3 + ". " + "Sum Squares " + x + " = " + sumOfSquaresFunction.getFunctionValue(x));
            ++countCos3;
        }
        System.out.println("\nТабулирование функций");
        TabulatedFunction tabulatedSum = TabulatedFunctions.tabulate(sumOfSquaresFunction, 0, Math.PI, 10);
        for (double x=0; x<=Math.PI; x+=0.1) {
            System.out.println("Sum Squares " + x + " = " + tabulatedSum.getFunctionValue(x));
        }

        // Экспонента
        System.out.println("\nЭкспонента");
        TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11);
        for (FunctionPoint point : tabulatedExp.getPoints()){
            System.out.println(point.getX() + " : " + point.getY());
        }
        System.out.println("Count of dots: " + tabulatedExp.getPointsCount());

        try (Writer writer = new FileWriter("expFunction.txt")){
            System.out.println("Attempting to write to file.");
            TabulatedFunctions.writeTabulatedFunction(tabulatedExp, writer);
            System.out.println("Writing completed successfully.");
        } catch (IOException e){
            e.printStackTrace();
        }

        try (Reader reader = new FileReader("expFunction.txt")){
            System.out.println("Attempting to read to file.");
            TabulatedFunction readFunc = TabulatedFunctions.readTabulatedFunction(reader);
            for (FunctionPoint point : readFunc.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("Reading completed successfully.");
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("End of tests");
    }
}

