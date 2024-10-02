package threads;

import functions.Function;
import functions.basic.Log;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Generator extends Thread{
    private final Task task;
    private final Semaphore semaphore;

    public Generator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < task.getTaskCount(); i++) {
            try {
                // Запрашиваем доступ к семафору для записи
                semaphore.acquire();

                // Генерация случайного основания для логарифма [2.0, 10.0)
                double base = 2.0 + 8.0 * random.nextDouble();
                Function logFunction = new Log(base);

                // Генерация случайных границ и шага
                double left = 100.0 * random.nextDouble(); // [0, 100)

                double right = 100.0 + 100.0 * random.nextDouble(); // [100, 200)

                double step = random.nextDouble();


                // Заполнение объекта Task
                task.setFunction(logFunction);
                task.setLeftBound(left);
                task.setRightBound(right);
                task.setStep(step);

                // Вывод информации о текущей задаче
                System.out.printf(i + ". Source: %.4f %.4f %.4f%n", left, right, step);

                // Освобождаем семафор после записи
                semaphore.release();

            } catch (InterruptedException e) {
                System.out.println("Generator interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Отправка сигнала завершения (можно использовать специальное значение)
        try {
            semaphore.acquire();
//            task.setFinished(true);
            semaphore.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
