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
        for (int i = 0; i < task.getTaskCount(); i++) {
            try {
                // Запрашиваем доступ к семафору для чтения
                semaphore.acquire();

//                // Проверка на завершение генерации
//                if (task.isFinished()) {
//                    semaphore.release();
//                    break;
//                }

                // Чтение данных из объекта Task
                Function currentFunction = task.getFunction();
                double left = task.getLeftBound();
                double right = task.getRightBound();
                double step = task.getStep();

                // Освобождаем семафор после чтения
                semaphore.release();

                // Проверка на наличие функции
                if (currentFunction == null) {
                    System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, Double.NaN);
                    continue;
                }

                // Вычисление интеграла
                double result = Functions.integrate(currentFunction, left, right, step);

                // Вывод результата в консоль
                System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, result);

            } catch (InterruptedException e) {
                System.out.println("Integrator interrupted");
                Thread.currentThread().interrupt();
                break;
            }

        }
    }
}
