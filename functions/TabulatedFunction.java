package functions;

import functions.exceptions.FunctionPointIndexOutOfBoundsException;
import functions.exceptions.InappropriateFunctionPointException;

public interface TabulatedFunction extends Function{

    public FunctionPoint[] getPoints();

    // Метод для получения количества точек
    public int getPointsCount();

    // Метод для получения точки по индексу
    public FunctionPoint getPoint(int index);

    // Метод для замены точки по индексу
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    public double getPointX(int index);

    // Метод для установки значения x точки по индексу
    public void setPointX(int index, double x) throws InappropriateFunctionPointException;

    // Метод для получения значения y точки по индексу
    public double getPointY(int index);

    // Метод для установки значения y точки по индексу
    public void setPointY(int index, double y);

    // Метод для удаления точки по индексу
    public void deletePoint(int index);

    // Метод для добавления точки
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
}

