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
            while (true) {
                // Ожидание доступной задачи
                semaphore.acquire();

                // Проверка прерывания после пробуждения
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Integrator: Прерывание выполнено. Завершение работы.");
                    break;
                }

                // Проверка окончания генерации и отсутствия доступных задач
                if (task.isFinished() && !task.isAvailable()) {
                    System.out.println("Integrator: Завершение работы, все задачи обработаны.");
                    break;
                }

                // Проверка, доступна ли новая задача
                if (task.isAvailable()) {
                    // Получение данных задачи
                    Function func = task.getFunction();
                    double left = task.getLeftBound();
                    double right = task.getRightBound();
                    double step = task.getStep();

                    // Пометка задачи как обработанной
                    task.setAvailable(false);

                    // Обработка задачи вне синхронизированного блока
                    double result = Functions.integrate(func, left, right, step);

                    // Вывод результата в консоль
                    System.out.printf("Result: %.4f %.4f %.4f %.4f%n", left, right, step, result);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Integrator: " + e.getMessage());
        }
    }
}
