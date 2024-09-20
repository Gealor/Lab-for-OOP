package functions;

import functions.exceptions.FunctionPointIndexOutOfBoundsException;
import functions.exceptions.InappropriateFunctionPointException;

import java.io.Serializable;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable {
    // Описываю этот класс как вложенный в LinkedListTabulatedFunction, чтобы обеспечить прямой доступ к элементам связного списка
    // но при этом обеспечить высокий уровень инкапсуляции, делая его недоступным из внешних классов

    private class FunctionNode {
        private FunctionPoint data;
        private FunctionNode prev;
        private FunctionNode next;

        // Конструктор
        public FunctionNode(FunctionPoint data){
            this.data = data;
        }

        public FunctionNode() { this.data = null; }

        // Геттеры и сеттеры
        public FunctionPoint getData() {
            return data;
        }

        public void setData(FunctionPoint data) {
            this.data = data;
        }

        public FunctionNode getPrev() {
            return prev;
        }

        public void setPrev(FunctionNode prev) {
            this.prev = prev;
        }

        public FunctionNode getNext() {
            return next;
        }

        public void setNext(FunctionNode next) {
            this.next = next;
        }

        public void deleteNode(){
            this.setPrev(null);
            this.setNext(null);
            this.setData(null);
        }
    }

    private FunctionNode head;
    private int size;

    // Конструктор, создающий табулированную функцию с заданным количеством точек
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (pointsCount < 2 || leftX >= rightX) {
            throw new IllegalArgumentException("Invalid parameters for TabulatedFunction constructor.");
        }

        head = new FunctionNode(); // Инициализация головы
        head.setNext(head);        // Ссылка next на саму голову
        head.setPrev(head);        // Ссылка prev на саму голову
        size = 0;

        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * interval;
            addNodeToTail(new FunctionPoint(x,0));
        }
    }

    // Конструктор для инициализации функции с заданными значениями
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        int pointsCount = values.length;

        if (values.length < 2 || leftX >= rightX) {
            throw new IllegalArgumentException("Values array should contain at least 2 elements.");
        }

        head = new FunctionNode(); // Инициализация головы
        head.setNext(head);        // Ссылка next на саму голову
        head.setPrev(head);        // Ссылка prev на саму голову
        size = 0;

        double interval = (rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * interval;
            addNodeToTail(new FunctionPoint(x, values[i]));
        }
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Points array cannot be null.");
        }
        if (points.length < 2) {
            throw new IllegalArgumentException("There must be at least two points.");
        }

        for (int i = 1; i < points.length; i++) {
            if (points[i-1].getX() >= points[i].getX()) {
                throw new IllegalArgumentException("Points must be sorted by x-coordinate in ascending order.");
            }
        }

        head = new FunctionNode(); // Инициализация головы
        head.setNext(head);        // Ссылка next на саму голову
        head.setPrev(head);        // Ссылка prev на саму голову
        size = 0;

        for (int i = 0; i < points.length; i++) {
            addNodeToTail(points[i]);
        }
    }

    // Получение массива точек
    @Override
    public FunctionPoint[] getPoints() {
        FunctionPoint[] points = new FunctionPoint[size];
        FunctionNode current = head.getNext();

        for (int i = 0; i < size; i++) {
            points[i] = current.getData();
            current = current.getNext();
        }

        return points;
    }

    // Получение левой границы области определения
    @Override
    public double getLeftDomainBorder() {
        if (size == 0) {
            throw new IllegalStateException("The list is empty.");
        }
        return head.getNext().getData().getX();
    }

    // Получение правой границы области определения
    @Override
    public double getRightDomainBorder() {
        if (size == 0) {
            throw new IllegalStateException("The list is empty.");
        }
        return getNodeByIndex(size - 1).getData().getX();
    }

    // Метод для получения значения функции в точке x
    @Override
    public double getFunctionValue(double x) {
        // Если x вне границ области определения, возвращаю NaN
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        FunctionNode current = head.getNext();

        while (current.getNext() != null) {
            double x1 = current.getData().getX();
            double x2 = current.getNext().getData().getX();
            if (x1 <= x && x <= x2) {
                double y1 = current.getData().getY();
                double y2 = current.getNext().getData().getY();
                return y1 + (x - x1) * (y2 - y1) / (x2 - x1);
            }
            current = current.getNext();
        }

        // На случай, если x точно совпадает с координатой любой из точек
        current = head.getNext();
        while (current != null) {
            if (current.getData().getX() == x) {
                return current.getData().getY();
            }
            current = current.getNext();
        }

        // По умолчанию возвращаю NaN из класса Double
        return Double.NaN;
    }

    // Получение количества точек
    @Override
    public int getPointsCount() {
        return size;
    }

    // Получение точки по индексу
    @Override
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        return getNodeByIndex(index).getData();
    }

    // Установка новой точки по индексу
    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        if ((index > 0 && point.getX() <= getNodeByIndex(index - 1).getData().getX()) ||
            (index < size - 1 && point.getX() >= getNodeByIndex(index + 1).getData().getX())) {
            throw new InappropriateFunctionPointException("Invalid x coordinate: " + point.getX());
        }
        getNodeByIndex(index).setData(point);
    }

    // Получение значения X точки по индексу
    @Override
    public double getPointX(int index) {
        if (index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        return getNodeByIndex(index).getData().getX();
    }

    // Установка значения X точки по индексу
    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
//        FunctionNode node = getNodeByIndex(index);

        if ((index > 0 && x <= getNodeByIndex(index - 1).getData().getX()) ||
            (index < getPointsCount() - 1 && x >= getNodeByIndex(index + 1).getData().getX())) {
            throw new InappropriateFunctionPointException("Invalid x coordinate: " + x);
        }
//        node.getData().setX(x);
        getNodeByIndex(index).getData().setX(x);
    }

    // Получение значения Y точки по индексу
    @Override
    public double getPointY(int index) {
        if (index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        return getNodeByIndex(index).getData().getY();
    }

    // Установка значения Y точки по индексу
    @Override
    public void setPointY(int index, double y) {
        if (index < 0 || index >= getPointsCount()) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        getNodeByIndex(index).getData().setY(y);
    }

    // Удаление точки по индексу
    @Override
    public void deletePoint(int index) {
        int count = getPointsCount();

        if (index < 0 || index >= count) {
            throw new FunctionPointIndexOutOfBoundsException("Invalid index: " + index);
        }
        if (count <= 2) {
            throw new IllegalStateException("Cannot delete point. At least two points must remain.");
        }
        deleteNodeByIndex(index);
    }

    // Добавление точки
    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (getPointsCount() > 0) {
            if (point.getX() <= getLeftDomainBorder() || point.getX() >= getRightDomainBorder()) {
                throw new InappropriateFunctionPointException("Point x-coordinate must be within existing points' x-range.");
            }
            FunctionNode current = head.getNext();
            for (int i = 0; i < size; i++) {
                if (current.getData().getX() >= point.getX()) {
                    addNodeByIndex(i, point);
                    return;
                }
                current = current.getNext();
            }
        }
        else {
            addNodeToTail(point);
        }
    }

    private FunctionNode getNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        }
        FunctionNode current;
        if (index < size/2) {
            current = head.getNext();
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        }
        else {
            current=head.getPrev();
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    private FunctionNode addNodeToTail(FunctionPoint data) {
        FunctionNode newNode = new FunctionNode(data);

        FunctionNode tail = head.getPrev();
        tail.setNext(newNode);
        newNode.setPrev(tail);
        newNode.setNext(head);
        head.setPrev(newNode);

        size++;
        return newNode;
    }

    private FunctionNode addNodeByIndex(int index, FunctionPoint data) {
        if (index < 0 || index > size) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        }
        FunctionNode newNode = new FunctionNode(data);
        if (index == size) {
            return addNodeToTail(data);
        }
        else {
            FunctionNode nextNode = getNodeByIndex(index);
            FunctionNode prevNode = nextNode.getPrev();

            newNode.setPrev(prevNode);
            newNode.setNext(nextNode);
            prevNode.setNext(newNode);
            nextNode.setPrev(newNode);
        }
        size++;
        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        }
        FunctionNode nodeToDelete = getNodeByIndex(index);
        FunctionNode prevNode = nodeToDelete.getPrev();
        FunctionNode nextNode = nodeToDelete.getNext();

        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);

        nodeToDelete.deleteNode();

        size--;
        return nodeToDelete;
    }
}
