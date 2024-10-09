package threads;

import functions.Function;
import functions.Functions;

public class SimpleIntegrator implements Runnable {
    private final Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getTaskCount(); i++){
            synchronized (task) {
                if (task.getFunction() == null) {
                    continue;
                }
                Function currentFunction = task.getFunction();
                double left = task.getLeftBound();
                double right = task.getRightBound();
                double step = task.getStep();

                double result = Functions.integrate(currentFunction, left, right, step);
                System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, result);
            }
        }

    }
//    @Override
//    public void run() {
//        for (int i = 0; i < task.getTaskCount(); i++) {
//            synchronized (task) {
//                Function currentFunction = task.getFunction();
//                double left = task.getLeftBound();
//                double right = task.getRightBound();
//                double step = task.getStep();
//
//                // Проверка на null, чтобы избежать ошибки
//                if (currentFunction == null) {
//                    System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, Double.NaN);
//                    continue; // Пропускаем итерацию, если функция не задана
//                }
//
//                // Вычисление интеграла
//                double result;
//                result = Functions.integrate(currentFunction, left, right, step);
//
//                // Вывод результата в консоль
//                System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, result);
//
//                try{
//                    task.notify();
//                    task.wait();
//                } catch (InterruptedException e){
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//    }
}
