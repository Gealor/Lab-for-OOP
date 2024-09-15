package functions;

import java.io.*;

public final class TabulatedFunctions {

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

        // Предполагаем, что ArrayTabulatedFunction - это класс, реализующий TabulatedFunction
        return new ArrayTabulatedFunction(arrayPoints);
    }

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
            return new ArrayTabulatedFunction(arrayPoints);
        }
    }

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
        return new ArrayTabulatedFunction(arrayPoints);
    }
}
