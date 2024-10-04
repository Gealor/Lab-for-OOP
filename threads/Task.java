package threads;

import functions.Function;

public class Task {
    private Function function;
    private double leftBound;
    private double rightBound;
    private double step;
    private int taskCount;


    public Task(){
    }

    // Геттеры и сеттеры

    public synchronized Function getFunction() {
        return function;
    }

    public synchronized void setFunction(Function function) {
        this.function = function;
    }

    public synchronized double getLeftBound() {
        return leftBound;
    }

    public synchronized void setLeftBound(double leftBound) {
        this.leftBound = leftBound;
    }

    public synchronized double getRightBound() {
        return rightBound;
    }

    public synchronized void setRightBound(double rightBound) {
        this.rightBound = rightBound;
    }

    public synchronized double getStep() {
        return step;
    }

    public synchronized void setStep(double step) {
        this.step = step;
    }

    public synchronized int getTaskCount() {
        return taskCount;
    }

    public synchronized void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
