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
            // результат отличается от предыдущего, т.к я использую TabulatedFunction(который является интерфейсом для
            // ArrayTabulatedFunction и LinkedListTabulatedFunction), а там в методе getFunctionValue используется линейная интерполяция(т.е значение между точками,
            // вычисляется относительно этих фиксированных точек).
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

        // Логарифм
        System.out.println("\nЛогарифм");
        TabulatedFunction tabulatedLog = TabulatedFunctions.tabulate(new Log(2), 1, 10, 10);
//        for (FunctionPoint point : tabulatedLog.getPoints()){
//            System.out.println(point.getX() + " : " + point.getY());
//        }
        try (OutputStream outputStream = new FileOutputStream("logFunction.bin")){
            System.out.println("Attempting to write to file.");
            TabulatedFunctions.outputTabulatedFunction(tabulatedLog, outputStream);
            System.out.println("Writing completed successfully.");
        } catch (IOException e){
            e.printStackTrace();
        }

        try (InputStream inputStream = new FileInputStream("logFunction.bin")){
            System.out.println("Attempting to read to file.");
            TabulatedFunction readLog = TabulatedFunctions.inputTabulatedFunction(inputStream);
            for (FunctionPoint point : readLog.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
            System.out.println("Reading completed successfully.");
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("End of tests");


        // Serializable на примере log(e)
        System.out.println("\nSerializable на примере log(e)");
        Function logFunc = new Log(new Exp());
        TabulatedFunction lnFunction = TabulatedFunctions.tabulate(new Log(Math.E), 1, 10, 10);
        // Сериализация объекта
        try (FileOutputStream fileOut = new FileOutputStream("lnFunction.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(lnFunction);
            System.out.println("Сериализация завершена.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десериализация объекта
        try (FileInputStream fileIn = new FileInputStream("lnFunction.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            TabulatedFunction deserializedLn = (ArrayTabulatedFunction) in.readObject();
            System.out.println("Десериализация завершена.");
            for (FunctionPoint point : deserializedLn.getPoints()){
                System.out.println(point.getX() + " : " + point.getY());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

