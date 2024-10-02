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

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public double getLeftBound() {
        return leftBound;
    }

    public void setLeftBound(double leftBound) {
        this.leftBound = leftBound;
    }

    public double getRightBound() {
        return rightBound;
    }

    public void setRightBound(double rightBound) {
        this.rightBound = rightBound;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
