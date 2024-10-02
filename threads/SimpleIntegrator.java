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
        while (true) {
            synchronized (task) {
                // Жду, пока появится новая задача или флаг завершения
                while (!task.isAvailable() && !task.isFinished()) {
                    try {
                        task.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Integrator interrupted while waiting.");
                        return;
                    }
                }

                // Если флаг завершения установлен и задачи не доступны, выходим
                if (task.isFinished() && !task.isAvailable()) {
                    break;
                }

                // Получаем данные задачи
                Function currentFunction = task.getFunction();
                double left = task.getLeftBound();
                double right = task.getRightBound();
                double step = task.getStep();

                // Устанавливаем флаг доступности в false, чтобы сигнализировать производителю
                task.setAvailable(false);

                // Уведомляем производителя о готовности к следующей задаче
                task.notifyAll();

                // Обработка задачи (вне синхронизированного блока для повышения производительности)
                double result;
                if (currentFunction == null) {
                    result = Double.NaN;
                } else {
                    result = Functions.integrate(currentFunction, left, right, step);
                }

                // Вывод результата в консоль
                System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, result);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Integrator interrupted during sleep.");
                return;
            }
        }
    }
}