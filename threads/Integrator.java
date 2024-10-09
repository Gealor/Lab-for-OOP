package threads;

import functions.Function;
import functions.Functions;

import java.util.concurrent.Semaphore;

public class Integrator extends Thread{
    private final Task task;
    private final Semaphore semaphore;

    public Integrator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < task.getTaskCount(); i++) {
                semaphore.acquire();

                // Получение данных задачи
                Function func = task.getFunction();
                double left = task.getLeftBound();
                double right = task.getRightBound();
                double step = task.getStep();


                // Обработка задачи
                double result;
                if (func == null) {
                    result = Double.NaN;
                    // Вывод результата в консоль
                    System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, result);
                } else {
                    result = Functions.integrate(func, left, right, step);
                    // Вывод результата в консоль
                    System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, result);
                }



                semaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Integrator: " + e.getMessage());
        }
    }
}
