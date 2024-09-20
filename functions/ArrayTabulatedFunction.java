package functions;

import functions.exceptions.FunctionPointIndexOutOfBoundsException;
import functions.exceptions.InappropriateFunctionPointException;

public class ArrayTabulatedFunction implements TabulatedFunction {
    private FunctionPoint[] points;

    // Конструктор, создающий табулированную функцию с заданным количеством точек
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (pointsCount < 2 || leftX >= rightX) {
            throw new IllegalArgumentException("Invalid parameters for TabulatedFunction constructor.");
        }
        points = new FunctionPoint[pointsCount];
        // Вычисляю интервал для размещения точки по координате x
        //                левая граница - правая граница / количество интервалов(которых на 1 единицу меньше точек!!!!!)
        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(leftX + i * interval, 0);
        }
    }

    // Конструктор, создающий табулированную функцию с заданными значениями функции
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        int pointsCount = values.length;

        if (pointsCount < 2) {
            throw new IllegalArgumentException("Values array should contain at least 2 elements.");
        }
        
        points = new FunctionPoint[pointsCount];
        
        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            points[i] = new FunctionPoint(leftX + i * interval, values[i]);
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] points){
        if (points == null) {
            throw new IllegalArgumentException("Points array cannot be null.");
        }

        if (points.length < 2) {
            throw new IllegalArgumentException("There must be at least two points.");
        }

        // Проверка на упорядоченность по возрастанию абсциссы
        for (int i = 1; i < points.length; i++) {
            if (points[i-1].getX() > points[i].getX()) {
                throw new IllegalArgumentException("Points must be sorted by x-coordinate in ascending order.");
            }
        }

        // Создаем копию массива для обеспечения инкапсуляции
        this.points = new FunctionPoint[points.length];
        System.arraycopy(points, 0, this.points, 0, points.length);
    }

    @Override
    public FunctionPoint[] getPoints() {
        return points;
    }
    @Override
    public double getLeftDomainBorder(){
        return points[0].getX();
    }
    @Override
    public double getRightDomainBorder() {
        return points[points.length - 1].getX();
    }

    // Метод для получения значения функции в точке x
    @Override
    public double getFunctionValue(double x) {
        // Если x вне границ области определения, возвращаю NaN
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        // Линейная интерполяция между точками
        for (int i = 0; i < points.length - 1; i++) {
            double x1 = points[i].getX();
            double x2 = points[i + 1].getX();
            if (x1 <= x && x <= x2) {
                double y1 = points[i].getY();
                double y2 = points[i + 1].getY();
                return y1 + (x - x1) * (y2 - y1) / (x2 - x1);
            }
        }

        // На случай, если x точно совпадает с координатой любой из точек
        for (FunctionPoint point : points) {
            if (point.getX() == x) {
                return point.getY();
            }
        }

        // По умолчанию возвращаю NaN из класса Double
        return Double.NaN;
    }

    // Метод для получения количества точек
    @Override
    public int getPointsCount() {
        return points.length;
    }

    // Метод для получения точки по индексу
    @Override
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        return points[index];
    }

    // Метод для замены точки по индексу
    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException{
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }

        // Проверяю точку, чтобы она соответствовала порядку точек в массиве
        if (index > 0 && point.getX() <= points[index - 1].getX() ||
            index < points.length - 1 && point.getX() >= points[index + 1].getX()) {
            throw new InappropriateFunctionPointException("Invalid x coordinate: " + point.getX());
        }
        points[index] = point;
    }
    @Override
    public double getPointX(int index) {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        return getPoint(index).getX();
    }


    // Метод для установки значения x точки по индексу
    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index > 0 && x <= points[index - 1].getX() ||
            index < points.length - 1 && x >= points[index + 1].getX()) {
            throw new InappropriateFunctionPointException("Invalid x coordinate: " + x);
        }
        points[index].setX(x);
    }

    // Метод для получения значения y точки по индексу
    @Override
    public double getPointY(int index) {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        return getPoint(index).getY();
    }


    // Метод для установки значения y точки по индексу
    @Override
    public void setPointY(int index, double y) {
        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        points[index].setY(y);
    }

    // Метод для удаления точки по индексу
    @Override
    public void deletePoint(int index) {
        int count = getPointsCount();

        if (index < 0 || index >= points.length) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        if (count <= 2) {
            throw new IllegalStateException("Cannot delete point. At least two points must remain.");
        }
        FunctionPoint[] newPoints = new FunctionPoint[getPointsCount()-1];
        // Копирую первые элементы массива до элемента, который необходимо удалить
        System.arraycopy(points, 0, newPoints, 0, index);
        // Копирую элементы массива, начиная с точки index + 1, на позицию index, тем самым сдвигая все элементы влево и удаляя элемент на позиции index.
        System.arraycopy(points, index + 1, newPoints, index, count - index - 1);
        this.points = newPoints;
    }

    // Метод для добавления точки
    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{
        if (points.length > 0) {
            if (point.getX() <= points[0].getX() || point.getX() >= points[points.length - 1].getX()) {
                throw new InappropriateFunctionPointException("Point x-coordinate must be within existing points' x-range.");
            }
        }
        FunctionPoint[] newPoints = new FunctionPoint[getPointsCount() + 1];
        int i = 0;
        while (i < points.length && points[i].getX() < point.getX()) {
            newPoints[i] = points[i];
            i++;
        }
        newPoints[i] = point;
        while (i < points.length) {
            newPoints[i + 1] = points[i];
            i++;
        }
        this.points = newPoints;
    }

    @Override
    public String toString(){
        int size = getPointsCount();
        String result = "{";
        for (int i=0; i<size; i++){
            result += points[i].toString();
            if (i < size-1){
                result+=", ";
            }
        }
        result += "}";
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if (this==obj){
            return true;
        }
        if (!(obj instanceof TabulatedFunction)){
            return false;
        }
        if (obj instanceof ArrayTabulatedFunction){
            ArrayTabulatedFunction other = (ArrayTabulatedFunction) obj;
            if (this.getPointsCount() != other.getPointsCount()){
                return false;
            }
            for (int i=0; i < this.getPointsCount(); i++){
                if (!this.points[i].equals(other.points[i])){
                    return false;
                }
            }
            return true;
        }
        // Общий случай для других реализаций TabulatedFunction
        TabulatedFunction other = (TabulatedFunction) obj;
        if (this.getPointsCount() != other.getPointsCount()){
            return false;
        }
        for (int i=0; i<this.getPointsCount(); i++){
            if (!this.getPoint(i).equals(other.getPoint(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode(){
        int bits = getPointsCount();
        for (FunctionPoint point : this.getPoints()){
            bits ^= point.hashCode();
        }
        return bits;
    }

    @Override
    public ArrayTabulatedFunction clone() {
        try {
            ArrayTabulatedFunction cloned = (ArrayTabulatedFunction) super.clone();
            cloned.points = new FunctionPoint[this.getPointsCount()];
            for (int i = 0; i < this.points.length; i++) {
                cloned.points[i] = this.points[i].clone(); // Глубокое клонирование
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

