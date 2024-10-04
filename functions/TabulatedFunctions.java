package functions;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class TabulatedFunctions {

    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory newFactory) {
        if (newFactory == null) {
            throw new IllegalArgumentException("The factory can't be null.");
        }
        factory = newFactory;
    }

    public static TabulatedFunction createTabulatedFunction(double left, double right, int pointsCount){
        return factory.createTabulatedFunction(left, right, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double left, double right, double[] points){
        return factory.createTabulatedFunction(left, right, points);
    };

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points){
        return factory.createTabulatedFunction(points);
    }



    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cls, double left, double right, int pointsCount){
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(double.class, double.class, int.class);
            return constructor.newInstance(left, right, pointsCount);
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            throw new IllegalArgumentException("Error creating TabulatedFunction instance", e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction>cls ,double left, double right, double[] points){
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(double.class, double.class, double[].class);
            return constructor.newInstance(left, right, points);
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            throw new IllegalArgumentException("Error creating TabulatedFunction instance", e);
        }
    };

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction>cls, FunctionPoint[] points){
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(FunctionPoint[].class);
            return constructor.newInstance((Object) points); // Привожу к типу Object, чтобы массив интерпретировался как единый объект, а не набор параметров
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            throw new IllegalArgumentException("Error creating TabulatedFunction instance", e);
        }
    }



// ---------------------------------------------------------------------------------------------
    // Приватный конструктор предотвращает создание экземпляров класса
    private TabulatedFunctions() {
        throw new UnsupportedOperationException("Instance creation is not allowed");
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (function == null) {
            throw new IllegalArgumentException("Function cannot be null");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Points count must be at least 2");
        }
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Left bound must be less than right bound");
        }

        FunctionPoint[] arrayPoints = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + step * i;
            double y = function.getFunctionValue(x);
            arrayPoints[i] = new FunctionPoint(x,y);
        }

        return createTabulatedFunction(arrayPoints);
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> cls, Function function, double leftX, double rightX, int pointsCount) {
        if (function == null) {
            throw new IllegalArgumentException("Function cannot be null");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Points count must be at least 2");
        }
        if (leftX >= rightX) {
            throw new IllegalArgumentException("Left bound must be less than right bound");
        }

        FunctionPoint[] arrayPoints = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + step * i;
            double y = function.getFunctionValue(x);
            arrayPoints[i] = new FunctionPoint(x,y);
        }

        return createTabulatedFunction(cls, arrayPoints);
    }

// -----------------------------------------------------------------------------------------------------------

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        try (DataOutputStream dataOut = new DataOutputStream(out)) {
            int pointsCount = function.getPointsCount();
            dataOut.writeInt(pointsCount);
            for (int i = 0; i < pointsCount; i++) {
                dataOut.writeDouble(function.getPointX(i));
                dataOut.writeDouble(function.getPointY(i));
            }
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        try (DataInputStream dataIn = new DataInputStream(in)) {
            int pointsCount = dataIn.readInt();
            FunctionPoint[] arrayPoints = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; i++) {
                double x = dataIn.readDouble();
                double y = dataIn.readDouble();
                arrayPoints[i] = new FunctionPoint(x, y);
            }
            return createTabulatedFunction(arrayPoints);
        }
    }

    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> cls, InputStream in) throws IOException {
        try (DataInputStream dataIn = new DataInputStream(in)) {
            int pointsCount = dataIn.readInt();
            FunctionPoint[] arrayPoints = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; i++) {
                double x = dataIn.readDouble();
                double y = dataIn.readDouble();
                arrayPoints[i] = new FunctionPoint(x, y);
            }
            return createTabulatedFunction(cls, arrayPoints);
        }
    }

// --------------------------------------------------------------------------------------------------------

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(out)) {
            int pointsCount = function.getPointsCount();
            writer.write(Integer.toString(pointsCount));
            writer.write(" ");
            for (int i = 0; i < pointsCount; i++) {
                writer.write(Double.toString(function.getPointX(i)));
                writer.write(" ");
                writer.write(Double.toString(function.getPointY(i)));
                writer.write(" ");
            }
            writer.flush();
            // тест
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();
        int pointsCount = (int)tokenizer.nval;
        FunctionPoint[] arrayPoints = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            tokenizer.nextToken();
            double x = tokenizer.nval;
            tokenizer.nextToken();
            double y = tokenizer.nval;
            arrayPoints[i] = new FunctionPoint(x, y);
        }
        return createTabulatedFunction(arrayPoints);
    }

    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction>cls, Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.nextToken();
        int pointsCount = (int)tokenizer.nval;
        FunctionPoint[] arrayPoints = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            tokenizer.nextToken();
            double x = tokenizer.nval;
            tokenizer.nextToken();
            double y = tokenizer.nval;
            arrayPoints[i] = new FunctionPoint(x, y);
        }
        return createTabulatedFunction(cls, arrayPoints);
    }
}
