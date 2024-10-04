package threads;
import functions.Function;
import functions.basic.Log;
import threads.Task;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Generator extends Thread {
    private final Task task;
    private final Semaphore semaphore;

    public Generator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            Random random = new Random();
            int totalTasks = task.getTaskCount();

            for (int i = 0; i < totalTasks; i++) {
                // Проверка на прерывание
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Generator: Прерывание выполнено. Завершение работы.");
                    break;
                }

                // Генерация функции и параметров задачи
                double base = 2.0 + 8.0 * random.nextDouble();
                Function logFunction = new Log(base);
                double left = 100.0 * random.nextDouble();
                double right = left + 100.0 * random.nextDouble(); // Обеспечиваем, что right > left
                double step = 0.1 + random.nextDouble(); // Минимальный шаг 0.1 для избежания бесконечного цикла

                // Подготовка задачи
                task.setFunction(logFunction);
                task.setLeftBound(left);
                task.setRightBound(right);
                task.setStep(step);
                task.setAvailable(true);

                // Вывод сообщения в консоль
                System.out.printf("%d. Source: %.4f %.4f %.4f%n", i, left, right, step);

                // Освобождение семафора для Integrator
                semaphore.release();
                Thread.sleep(10);
            }

            // Установка флага завершения генерации
            task.setFinished(true);

            // Освобождение семафора для Integrator на случай, если Integrator ждет
            semaphore.release();
        } catch (Exception e) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Generator: Прерывание во время генерации задач.");
                Thread.currentThread().interrupt(); // Установка статуса прерывания
            }
        }
    }
}
